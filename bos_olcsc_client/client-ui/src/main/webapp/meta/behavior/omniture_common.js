/* 
	This function provides DOM Scripting for Omniture if it's present. It also
	automagically defines these variables:
	
		- s.pageName
		- s.channel
		- s.hier1
		- s_objectID
		
	All variables are defined according to the guidelines conveyed by Shuang in
	her document entitled "Omniture SiteCatalyst Integration Training Document".
*/
	
OmnitureAssistant = 
{
	// If true, the key combination shift-6 ("^") will pop-up
	// a dialog displaying the current Omniture-related variables
	OMNITURE_VARIABLES_HOTKEY_ENABLED: true,
	
	// If DEVELOPMENT_MODE_ENABLED is set to true,
	// the code automatically outputs some status
	// data to Firebug (if Firebug is installed)
	DEVELOPMENT_MODE_ENABLED: true,

	// If DEVELOPMENT_MODE_PREFIX_ENABLED is set to true,
	// the DEVELOPMENT_MODE_PREFIX will be added to 
	// the beginning of all pageName & hier1 variables	
	DEVELOPMENT_MODE_PREFIX_ENABLED: false,
	
	DEVELOPMENT_MODE_PREFIX: 'Dev',
	
	HAS_SOBJECTID_EVENT_HANDLER_CLASSNAME: 'has-sobjectid-event-handler',
	
	// Omniture-related variables
	pageName: '',
	channel: '',
	hier1: '',

	/* 
		(Descriptions from the Word document)
	
		s.PageName
			"The s.pageName variable always starts with the category name, then the section name within the category and then the specific page."
			
			Translation:
			"The s.pageName variable always starts with the primary-navigation name, 
				then the secondary-navigation name within the category and 
				then the tertiary-navigation name."
		
		s.channel
			"The channel variable always equals to the category name."
			
			Translation:
			"The channel variable always equals to the primary-navigation name."
			
			Primary-Navigation Categories:
				* [blank] (for the home page)
				* My EZ Account
				* Unpaid Tolls & Violations
				* Toll Road Info
				* About HCTRA
		
		s.hier1
			"The hier1 variable is the same as the pageName except that hier1 is comma delimited whereas pageName is colon delimited."
	*/
	runAssistant: function()
	{
		var pageHierarchyArray = this._getPageHierarchyArray();
		
		// If we're in development mode, then there's been a prefix 
		// added to the pageHierarchyArray, making its length
		// one longer than it would normally be; this compensates
		// for that
		switch ((this.DEVELOPMENT_MODE_ENABLED) && (this.DEVELOPMENT_MODE_PREFIX_ENABLED))
		{
			case true:
				var pageHierarchyArrayLength = pageHierarchyArray.length - 1;
				break;
				
			case false:
				var pageHierarchyArrayLength = pageHierarchyArray.length;
				break;
		}
		
		switch (pageHierarchyArrayLength)
		{
			// In the case of the home page, there is no "hierarchy", so
			// that scenario has a special case
			case 0:
					// In this case, we're on the home page
					this.pageName = [pageHierarchyArray.join(': '), 'Home Page'].join(': ');
					this.channel = '';
					this.hier1 = pageHierarchyArray.join(', ');
					break;
					
			default:
					this.pageName = pageHierarchyArray.join(': ');
					
					switch ((this.DEVELOPMENT_MODE_ENABLED) && (this.DEVELOPMENT_MODE_PREFIX_ENABLED))
					{
						case true:
							// Combine the first and second since the first one is
							// automatically set to the development prefix
							this.channel = [ pageHierarchyArray[0], pageHierarchyArray[1] ].join(' - ');
							break;
							
						case false:
							this.channel = pageHierarchyArray[0]; // first element
							break;
					}
					
					this.hier1 = pageHierarchyArray.join(', ');
					break;
						
		} // end of switch statement
		
		this.resetOmnitureVariables(); // First, reset the Omniture variables
		this.setOmnitureVariables(); // Then, define the variables based on what was derived above
		
		/* If in development mode, set up the hotkeys for triggering the status 
			dialog box -- in this case, Alt-O */
		if (this.DEVELOPMENT_MODE_ENABLED)
		{
			Event.observe(document, 'keydown', this._hotkeyEventHandler.bindAsEventListener(this));
			Event.observe(document, 'keyup', this._hotkeyEventHandler.bindAsEventListener(this));
			Event.observe(document, 'keypress', this._hotkeyEventHandler.bindAsEventListener(this));			
		}
		
		/* 
			setClickHandlersForSObjectID() would normally be run here
		 	(sets the click handlers for anchors with "#"
		 	to define s_objectID) but it's a separate line-item
		 	in the DOMReady call toward the bottom since 
		 	it's not very important that s_objectID is 
		 	defined that quickly 
		*/

	},
	
	/* This returns the an array based on the body classes of the page with 
		some capitalization tweaks for easier reading */
	_getPageHierarchyArray: function()
	{
		var bodyElementClassNames = document.getElementsByTagName('body')[0].className.split(' ');
		
		var pageHierarchyArray = [];
		
		// If we're in development mode (and the development-mode prefix is also enabled), 
		// add the development-mode prefix to the beginning of the Page Hierarchy
		if ((this.DEVELOPMENT_MODE_ENABLED) && (this.DEVELOPMENT_MODE_PREFIX_ENABLED))
		{
			pageHierarchyArray.push(this.DEVELOPMENT_MODE_PREFIX);
		}
		
		for (var i = 0, bodyClassesLength = bodyElementClassNames.length; i < bodyClassesLength; i++)
		{
			var currentClassName = bodyElementClassNames[i];

			
			// Some classnames have special-case scenarios
			
			switch (currentClassName)
			{
				// The classname 'account-section' can be applied to multiple sections,
				// so it's ignored
				case 'account-section':
					continue;
					break;
				
				case 'manage-section':
					pageHierarchyArray.push('My EZ Account');
					continue;
					break;
					
				case 'violations-section':
					pageHierarchyArray.push('Unpaid Tolls & Violations');
					continue;
					break;
					
				case 'toll-road-info-section':
					pageHierarchyArray.push('Toll Road Information');
					continue;
					break;
					
				case 'about-section':
					pageHierarchyArray.push('About HCTRA');
					continue;
					break;
			}

			var classnameWords = currentClassName.split('-');
			
			// Skip over non-applicable classnames
			if (classnameWords[classnameWords.length - 1] != 'section')
			{
				continue;
			}
			
			
			var stringBuffer = [];
			
			/* Go through the splitClassname array and capitalize those guys,
			   adding them to tempArry (which is then .join()ed at the end) */
			
			// (The "classnameWords.length - 1" bit skips over the last word, 'section')
			for (j = 0, maxClassnameWordsIndex = classnameWords.length - 1; j < maxClassnameWordsIndex; j++)
			{
				var currentWord = classnameWords[j];
				
				switch (currentWord)
				{
					/* The following words/acronyms are put in ALL UPPERCASE
						rather than Mixed Case: */
					case 'ez':
					case 'tag':
					case 'eztag':
					case 'faq':
					case 'hctra':
						stringBuffer.push( currentWord.toUpperCase() );
						break;
						
					default:
						stringBuffer.push( currentWord.capitalize() );
						break;
						
				} // end of switch statement
				
			} // end of for-loop for reformatting words	
			
			if (stringBuffer.length)
			{
				pageHierarchyArray.push( stringBuffer.join(' ') );
			}
			
		} // end of for-loop for class names
		
		
		return pageHierarchyArray;
		
	},
	
	// This initializes the various Omniture variables to empty strings, as needed
	resetOmnitureVariables: function()
	{
		if (typeof s == 'undefined') return;
		
		/* 
			Then, reset each Omniture variable unless
			it's already been defined elsewhere 
			(such as some inline JavaScript in the given file)
		*/
		
		if (!s.pageName) s.pageName = '';
		if (!s.server) s.server = '';
		if (!s.channel) s.channel = '';
		if (!s.pageType) s.pageType = '';
		if (!s.prop1) s.prop1 = '';
		if (!s.prop2) s.prop2 = '';
		if (!s.prop3) s.prop3 = '';
		if (!s.prop4) s.prop4 = '';
		if (!s.prop5) s.prop5 = '';
		
		/* E-commerce Variables */
		if (!s.campaign) s.campaign = '';
		if (!s.state) s.state = '';
		if (!s.zip) s.zip = '';
		if (!s.events) s.events = '';
		if (!s.products) s.products = '';
		if (!s.purchaseID) s.purchaseID = '';
		if (!s.eVar1) s.eVar1 = '';
		if (!s.eVar2) s.eVar2 = '';
		if (!s.eVar3) s.eVar3 = '';
		if (!s.eVar4) s.eVar4 = '';
		if (!s.eVar5) s.eVar5 = '';
		
		/* Hierarchy Variables */
		if (!s.hier1) s.hier1 = '';
	},
	
	setOmnitureVariables: function()
	{
		if ((this.DEVELOPMENT_MODE_ENABLED) && (typeof console != 'undefined'))
		{
			console.log('s.pageName = "' + this.pageName + '"');
			console.log('s.channel = "' + this.channel + '"');
			console.log('s.hier1 = "' + this.hier1 + '"');
			console.log('s.events = "' + s.events + '"');
			console.log('s.eVar1 = "' + s.eVar1 + '"');
			console.log('s.eVar2 = "' + s.eVar2 + '"');
			console.log('s.eVar3 = "' + s.eVar3 + '"');
			console.log('s.eVar4 = "' + s.eVar4 + '"');
			console.log('s.eVar5 = "' + s.eVar5 + '"');
			console.log('s.zip = "' + s.zip + '"');
			console.log('s.state = "' + s.state + '"');
			console.log('s.products = "' + s.products + '"');
			console.log('s.purchaseID = "' + s.purchaseID + '"'); 
			console.log('s.prop1 = "' + s.prop1 + '"');
			console.log('s.prop2 = "' + s.prop2 + '"');
			console.log('s.prop3 = "' + s.prop3 + '"');
			console.log('s.prop4 = "' + s.prop4 + '"');
                        console.log('s.prop5 = "' + s.prop5 + '"');
		}
		
		if (typeof s == 'undefined') return;
		
		if ((this.DEVELOPMENT_MODE_ENABLED) && (typeof console != 'undefined'))
		{
			console.log('(Omniture\'s s_code.js is running)');
			
			// s_account represents the "bucket" into which the page's
			// stats are tracked
			console.log('s_account = "' + s_account + '"'); 
		}
		
		this.resetOmnitureVariables();
		
		s.pageName = this.pageName;
		s.channel = this.channel;
		s.hier1 = this.hier1;
		
		/* 
			The below bits are equivalent to this Omniture-native section of code:
			var s_code=s.t();if(s_code)document.write(s_code) 
		*/

		s_code = s.t();

		if (s_code)
		{
			if (typeof s_code == 'function')
			{
				s_code();
			}
			else
			{
				/* 
					s_code is generally approached with a document.write()
					point of view, but in this case its code is placed
					inside a <div></div> and appended to the document instead
				*/
				
				var sCodeElement = document.createElement('div');
				sCodeElement.setAttribute('id', 'omniture-scode-element');
				sCodeElement.innerHTML = s_code;

				document.body.appendChild(sCodeElement);
				
				if ((this.DEVELOPMENT_MODE_ENABLED) && (typeof console != 'undefined'))
				{
					console.info('Omniture\'s s_code output: "' + s_code + '"');
				}
				
				sCodeElement = null; // prevent memory leaks
			}
		} // end of conditional test for s_code
	},
	
	// This is assigned to the document's keypress event and, if
	// the caret is pressed, the Omniture variables info-box is shown
	_hotkeyEventHandler: function(event)
	{
		
		switch (event.type)
		{
			case 'keydown':
				if (event.altKey)
				{
					this.altKey = true;
				}
				else
				{
					this.altKey = false;
				}
				break;
				
			/* (Yes, the switch-statement fall-through here is intentional) */
			case 'keyup':
			case 'keypress':
			
				// Exit if the alt key isn't being held down
			 	if (!this.altKey) return;
			
				if (event.keyCode)
				{
					var code = event.keyCode;
				}
				else if (event.which)
				{
					var code = event.which;
				}
				
				/* 
					248: Option-O on OS X
					111: Alt-O on Windows (standards-compliant browsers)
					79: Alt-O on Windows/IE
				*/
				if ((code == 248) || (code == 111) || (code ==79))
				{
					this.altKey = false; // just to be sure
					Event.stop(event); // prevent event propagation
					this.showOmnitureVariables();
				}
				break;
				
		} // end of switch statement
		
	},
	
	/* This triggers an alert() that displays
		the current status of Omniture-related variables */
	showOmnitureVariables: function()
	{
		var stringBuffer = []; // initialize
		
		stringBuffer.push('<script language="javascript" type="text/javascript">');
		stringBuffer.push('<!-- ');
		
		if (typeof s == 'undefined')
		{
			stringBuffer.push("/* 's' is not defined. */");
			stringBuffer.push('');
		}
		else
		{
                        stringBuffer.push('s.pageName="' + s.pageName + '"');
			stringBuffer.push('s.server="' + s.server + '"');
			stringBuffer.push('s.channel="' + s.channel + '"');
			stringBuffer.push('s.pageType="' + s.pageType + '"');
			stringBuffer.push('s.prop1="' + s.prop1 + '"');
			stringBuffer.push('s.prop2="' + s.prop2 + '"');
			stringBuffer.push('s.prop3="' + s.prop3 + '"');
			stringBuffer.push('s.prop4="' + s.prop4 + '"');
			stringBuffer.push('s.prop5="' + s.prop5 + '"');
			stringBuffer.push('/* E-commerce Variables */');
			stringBuffer.push('s.campaign="' + s.campaign + '"');
			stringBuffer.push('s.state="' + s.state + '"');
			stringBuffer.push('s.zip="' + s.zip + '"');
			stringBuffer.push('s.events="' + s.events + '"');
			stringBuffer.push('s.products="' + s.products + '"');
			stringBuffer.push('s.purchaseID="' + s.purchaseID + '"');
			stringBuffer.push('s.eVar1="' + s.eVar1 + '"');
			stringBuffer.push('s.eVar2="' + s.eVar2 + '"');
			stringBuffer.push('s.eVar3="' + s.eVar3 + '"');
			stringBuffer.push('s.eVar4="' + s.eVar4 + '"');
			stringBuffer.push('s.eVar5="' + s.eVar5 + '"');
			stringBuffer.push('/* Hierarchy Variables */');
			stringBuffer.push('s.hier1="' + s.hier1 + '"');
		}
		
		stringBuffer.push[''];
		
		if (typeof s_code != 'undefined')
		{
			stringBuffer.push('var s_code="' + s_code + '"');
		}
		else
		{
			stringBuffer.push('var s_code=""');
		}
		
		stringBuffer.push('//--></script>');
		
		var alertString = stringBuffer.join('\n');
		
		alert(alertString);
	},
	
	/* This grabs all the anchor elements, checks if their
		"href" attributes contain "#" and, if so, adds an 
		event handler to set s_objectID
	*/
	setClickHandlersForSObjectID: function()
	{
		var anchorElements = $A( document.getElementsByTagName('a') );
		
		for (var i = 0, len = anchorElements.length; i < len; i++)
		{
			var currentAnchorElement = anchorElements[i];
			
			if ((currentAnchorElement.href) && (currentAnchorElement.href.length) && (currentAnchorElement.href.indexOf('#') != -1) )
			{
				if (!Element.hasClassName(currentAnchorElement, this.HAS_SOBJECTID_EVENT_HANDLER_CLASSNAME))
				{
					Event.observe(currentAnchorElement, 'click', OmnitureAssistant._setSObjectID.bindAsEventListener(OmnitureAssistant));
					
					// Give the element an extra class just to prevent multiple assignings of the 
					// event handler
					Element.addClassName(currentAnchorElement, this.HAS_SOBJECTID_EVENT_HANDLER_CLASSNAME);
					
				} // end of if-statement for checking if the anchor already has teh HAS_SOBJECTID_EVENT_HANDLER_CLASSNAME classname
				
			} // end of if-statement for general anchor element checking
			
		} // end of for-loop
	},
	
	/* 
		This function is assigned to the 'click' event handlers
		 for anchors containing page fragments 
		(such as <a href="#what-is-hctras-address">What is HCTRA's address?</a>).
		
		It then sets Omniture's s_objectID variable appropriately.
	*/
	_setSObjectID: function(event)
	{
		var anchorElement = Event.findElement(event, 'a');
		
		/*  
			stripTags() "strips a string of any HTML tag"
			while strip() "strips all leading and trailing whitespace from a string"
			
			http://www.prototypejs.org/api/string/stripTags 
			http://www.prototypejs.org/api/string/strip
		*/
		var linkText = anchorElement.innerHTML.stripTags().strip();
		
		// Make sure that there's actually something there before setting
		// s_objectID
		if (linkText.length)
		{
			s_objectID = linkText;
			
			if ((this.DEVELOPMENT_MODE_ENABLED) && (typeof console != 'undefined'))
			{
				console.log('s_objectID -> "' + linkText + '"');
			}
		}
		
	},
	
	// Flexible JavaScript Events - John Resig
	// http://ejohn.org/projects/flexible-javascript-events/
	//===========================================================================

	addEvent: function( obj, type, fn ) {
		if (obj.addEventListener)
			obj.addEventListener( type, fn, false );
		else if (obj.attachEvent)
		{
			obj["e"+type+fn] = fn;
			obj[type+fn] = function() { obj["e"+type+fn]( window.event ); }
			obj.attachEvent( "on"+type, obj[type+fn] );
		}
	},

	removeEvent: function( obj, type, fn ) {
		if (obj.removeEventListener)
			obj.removeEventListener( type, fn, false );
		else if (obj.detachEvent)
		{
			obj.detachEvent( "on"+type, obj[type+fn] );
			obj[type+fn] = null;
			obj["e"+type+fn] = null;
		}
	}
}; // end of OmnitureAssistant