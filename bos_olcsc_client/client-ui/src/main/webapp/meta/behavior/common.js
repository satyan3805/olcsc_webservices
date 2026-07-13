
/* Returns the class name of the argument or undefined if
   it's not a valid JavaScript object.
   http://magnetiq.com/2006/07/10/finding-out-class-names-of-javascript-objects/
*/
function getObjectClass(obj)
{
	if (obj && obj.constructor && obj.constructor.toString)
	{
		var arr = obj.constructor.toString().match(/function\s*(\w+)/);

		if (arr && arr.length == 2)
		{
			return arr[1];
		}
	}

	/*
		If it got this far, then the above technique didn't work
		(and it probably means we're in Safari).

		Since this uses	a whitelist approach, it's not as fool-proof
		as the above approach, but, hey, it's all we got :).

		FWIW, the types listed below were based off the type-list
		from the magnetiq.com page listed above the function.
	*/

	switch (obj.constructor)
	{
		case Number:
			return "Number";
			break;

		case Boolean:
			return "Boolean";
			break;

		case String:
			return "String";
			break;

		case Function:
			return "Function";
			break;

		case RegExp:
			return "RegExp";
			break;

		case Array:
			return "Array";
			break;

		case Date:
			return "Date";
			break;
	}


	// If it got this far, well, ruh-roh. There's not much more
	// we can do than just return "undefined".
	return undefined;
}

/* Returns "false" if the element or any of its parents are hidden (by
	being set to display:none). Otherwise, returns "true".

	Pulled from scrip.aculo.us' unittest.js (and adapted slightly
	as this was originally a method of the Test.Unit.Assertions class). */
function isVisible(element) {
    element = $(element);
    if(!element.parentNode) return true;

    if(element.style && Element.getStyle(element, 'display') == 'none')
      return false;

    return isVisible(element.parentNode);
}

// trim spaces from both sides of a string
String.prototype.trim = function(){
  var str = this.replace( /^\s+/g, "" );// strip leading
  return str.replace( /\s+$/g, "" );// strip trailing
}

// Formats a string in currency format
// (by design, this doesn't add any dollar signs to the
// beginning of the string, though that funcitonality
// could later be added if needed)
String.prototype.toCurrency = function()
{
	var workingString = this;

	// Check for the length and if it's empty, return "0.00"

	if (workingString.length == 0) return "0.00";

	// Check for the decimal point

	var numericalParts = workingString.split('.');

	// If there's no decimal point, append ".00" and exit

	if (numericalParts.length == 1) return(workingString + ".00");

	// Count the number of digits after the decimal point
	var digitsAfterDecimalPoint = numericalParts[1].length; // the second item in the array

	switch (digitsAfterDecimalPoint)
	{
		case 0:
			// If there're no digita (but there is a decimal point), append "00"
			workingString = workingString + "00";
			break;

		case 1:
			// If there's one, append a "0" and exit
			workingString = workingString + "0";
			break;

		case 2:
			// everything is A-Ok
			break;

		default:
			// more than 2 digits after the decimal point
			// .. so, trim it back to 2
			workingString = numericalParts[0] + "." + numericalParts[1].substr(0,2);
			break;
	}

	// Count the number of digits before the decimal point
	// If there aren't any, prepend a "0"
	if (numericalParts[0].length == 0) workingString = "0" + workingString;

	return workingString;
}


/* This extends the Element object in Prototype:
	http://www.andrewdupont.net/2006/04/15/objectified-custom-element-methods-in-prototype */


/* This walks up the tree until a given tagname is found.
	It accepts either a string or an array of strings. If a string
	is sent in, it looks for elements matching that string;
	or, if an array of strings is sent in, it checks for any
	tags within that array.

	It returns null if no matches are found. (i.e., if it ends
	up walking and hits the body element).

	Note: When using this, you must set somethign equal to this,
	such as:
		element = element.walkUpUntil('ul');

	... this function doesn't modify the original parameter, so
	that's why its result needs to be assigned somewhere.
*/

Element.addMethods(
{
	walkUpUntil : function(element, tagNames)
	{
		element = $(element);

		var parentElement = element; // initialize;

		switch (getObjectClass(tagNames))
		{
			case "String":

				// Set tagNames to lowercase for consistent matching
				tagNames = tagNames.toLowerCase();

				// Walk up the tree until you run into the relevant parent item (or the body element)
				while ((parentElement.tagName.toLowerCase() != tagNames) && (parentElement.tagName.toLowerCase() != "body"))
				{
					// If the element has no parent, exit
					// (this may occur if the element was removed from the DOM)
					if (!parentElement.parentNode.tagName)
					{
						return null;
					}

					// this statement could, in theory, be in an "else"
					// block for the previous if-statement, but that's
					// kinda irrelevant since the if statement exits
					// the function if the condition is found true
					parentElement = parentElement.parentNode;
				}

				break;

			case "Array":

				// First set all the strings to lowercase for consistent matching

				for (var i=0, len=tagNames.length; i < len; i++)
				{
					tagNames[i] = tagNames[i].toLowerCase();
				}


				var foundRelevantParent = false; // initialize
				var hitBodyElement = (parentElement.tagName.toLowerCase() == "body"); // initialize

				// Walk up the tree until you run into a relevant parent item (or the body element)
				while ((!foundRelevantParent) && (!hitBodyElement))
				{
					// Check the current element against each of the array items
					tagNames.each( function(tagToCheck)
					{
						if (parentElement.tagName.toLowerCase() == tagToCheck)
						{
							foundRelevantParent = true;
						}

					}.bind(this));

					// If a relevant parent wasn't found, move up the tree and update hitBodyElement
					if (!foundRelevantParent)
					{
						// If the element has no parent, exit
						// (this may occur if the element was removed from the DOM)
						if (!parentElement.parentNode.tagName)
						{
							return null;
						}

						// this statement could, in theory, be in an "else"
						// block for the previous if-statement, but that's
						// kinda irrelevant since the if statement exits
						// the function if the condition is found true
						parentElement = parentElement.parentNode;

						// Update "hitBodyElement" in case we ended up at the body element
						hitBodyElement = (parentElement.tagName.toLowerCase() == "body");
					}

				} // end of while-loop

				break;

			default:

				// If "tagNames" is neither a String nor an Array, return null
				// to indicate the bad input
				return null;
				break;

		}

		// If it walked all the way up the tree and found no appropriate parent elements, return null
		if (parentElement.tagName.toLowerCase() == "body")
		{
			return null;
		}

		// If it got this far, it was successful, so return the parentElement
		return parentElement;
	}
});


Common = {

	/* This is used to store anchors and their onlick handlers in the context
		of FAQ jobbies and other scenarios where a Script.aculo.us animation
		is tied to those anchors. The deal is that Omniture tends to toss
		around extra onclick handlers which can sludgify the animation.
		This way, however, the animation runs fine; then, once the animation
		completes, the onclick functions can be run manually */
	cachedAnchorList: [],

	/* Certain CSS bits require some non-semantic helper-elements;
		this function adds those unobtrusively */
	addHelperElements : function()
	{
		// Check if there's a common-navigation element and make sure the
		// common-navigation helper-element doesn't already exist ;)

		if ( ($('common-navigation')) && (! $('common-navigation-helper-element') ) )
		{
			/* If it got this far (by finding a common-navigation element
				but no common-navigation-helper element), add one */
			var commonNavigationHelperElement = document.createElement('div');
			commonNavigationHelperElement.setAttribute('id', 'common-navigation-helper-element');

			document.body.insertBefore(commonNavigationHelperElement, $('page'));
		}

		// Check if there's a masthead element and make sure the
		// masthead helper-element doesn't already exist

		if ( ($('masthead')) && (! $('masthead-helper-element') ) )
		{
			/* If it got this far (by finding a common-navigation element
				but no common-navigation-helper element), add one */
			var mastheadHelperElement = document.createElement('div');
			mastheadHelperElement.setAttribute('id', 'masthead-helper-element');

			document.body.insertBefore(mastheadHelperElement, $('page'));


			if ( ($('primary-navigation')) && (! $('primary-navigation-helper-element') ) )
			{
				/* If it got this far (by finding a common-navigation element
					but no common-navigation-helper element), add one */
				var primaryNavigationHelperElement = document.createElement('div');
				primaryNavigationHelperElement.setAttribute('id', 'primary-navigation-helper-element');

				mastheadHelperElement.appendChild(primaryNavigationHelperElement);
			}
		}


		// Check if there's a primary-content element and make sure the
		// primary-content helper-element doesn't already exist

		if ( ($('primary-content')) && (! $('primary-content-helper-element') ) )
		{
			/* If it got this far (by finding a common-navigation element
				but no common-navigation-helper element), add one */
			var mastheadHelperElement = document.createElement('div');
			mastheadHelperElement.setAttribute('id', 'primary-content-helper-element');

			document.body.insertBefore(mastheadHelperElement, $('page'));
		}

	},

	/* This set of code figures out the target element
		of an event: http://www.quirksmode.org/js/events_properties.html#target */
	getTargetElement : function(event)
	{
		var eventTargetElement;
		if (!event) var event = window.event;
		if (event.target) eventTargetElement = event.target;
		else if (event.srcElement) eventTargetElement = event.srcElement;
		if (eventTargetElement.nodeType == 3) // defeat Safari bug
			eventTargetElement = eventTargetElement.parentNode;

		return eventTargetElement;
	},

	/* This is a fork of the hideIbox() function
		in ibox.js. This version is meant for pages
		which have a static iBox; or, put another
		way, pages that only have a single lightbox
		that's hard-coded into the markup. */
	hideStaticIbox : function()
	{
		hideBG();
		var ibox = getElem('ibox_wrapper');
		ibox.style.display = "none";

		/* The original function had clearIboxContent() here */

		window.onscroll = null;
		window.onresize = null; // this was missing from the original function
	},

	/* This is intended to be assigned to a form field's onkeypress
		handler. Then, this can run an arbitrary function if
		a destructive keypress is entered. Destructive keypresses
		include all the regular keys except navigation keys
		(such as the arrows and the like.) */
	runFunctionIfDestructiveKeydown: function(event, functionToRun)
	{
		if (!event) var event = window.event;

		/*
			For onkeydown, Firefox, IE and Safari all use keyCode.
			See also this page: http://www.texotela.co.uk/keypress.php
		*/
		var key = event.keyCode;

		// 8: Backspace
		// 9: Tab (these don't trigger the function since they're used for moving between fields)
		// 13: Carriage return
		// 32: space

		// 33-40: arrow keys and other directional keys

		if ((key == 8) ||
			((key >= 10) && (key <= 13)) ||
		 	(key == 32) ||
		 	(key >=45))
		 {
			 functionToRun();
		 }

	},

	/*
		This goes through the cached anchor list and yanks off their
		onclick handlers, storing those handlers separately.

		The deal is that Omniture tends to toss around onclick handlers,
		which can sludgify the Script.aculo.us animation on those guys.

		So, the onclick handlers are pulled off and then, when those
		animations are fired, those handlers are run manually.
	*/
	processCachedAnchorClickHandlers: function()
	{
		// If Omniture isn't enabled for this page then, well, nevermind
		if (typeof s == 'undefined')
		{
			return;
		}

		// At this point, s must exist

		/* This keeps track of the number of anchors
			on which click handlers were found. Then, once
			all the anchors have been checked, if
			this counter is still at 0, then it means
			that the Omniture code hasn't run yet. */
		var numberOfAnchorsWithClickHandlers = 0; // initialize

		var cachedAnchorListLength = this.cachedAnchorList.length;

		for (var i = 0; i < cachedAnchorListLength; i++)
		{
			var currentAnchorObject = this.cachedAnchorList[i];

			var currentAnchor = currentAnchorObject.element;

			if ((currentAnchor.onclick) && (typeof currentAnchor.onclick == 'function'))
			{
				// console.log("Found click handler for link '" + currentAnchor.innerHTML + "'");
				currentAnchorObject.onclickFunction = currentAnchor.onclick.bind(currentAnchor);
				numberOfAnchorsWithClickHandlers++;
				currentAnchor.onclick = null;
			}

		} // end of for-loop

		/* If there are indeed anchors in the list but none of them
			have click handlers, then try running itself again in a little while.
			(This won't run indefinitely if Omniture isn't running since the beginning
			of this function checks for the existence of 's'.)
		*/
		if ((cachedAnchorListLength) && (!numberOfAnchorsWithClickHandlers))
		{
			window.setTimeout(this.processCachedAnchorClickHandlers.bind(this), 100);
		}

	}, // end of processCachedAnchorClickHandlers()

	/* This works alongside cacheAnchorClickHandlers. After calling a
		Script.aculo.us effect based on an anchor, its cached
		click handler is manually run based on the one that was tucked
		away earlier. */
	_findClickHandlerForAnchor: function(anchorElement)
	{
		for (var i = 0, len = this.cachedAnchorList.length; i < len; i++)
		{
			var currentAnchorObject = this.cachedAnchorList[i];

			if (currentAnchorObject.element == anchorElement)
			{
				return currentAnchorObject.onclickFunction;
			}
		}

		// If it got this far, then it must not have found one, so exit
		return null;
	},

	/*
		This processes a click on a dt element, such as on a FAQ item,
		to show the (dd-based) answer. It also accounts for accessibility
		by setting the target DD to a tabindex of "-1" and focusing it.

		(http://juicystudio.com/article/making-ajax-work-with-screen-readers.php)
	*/
	processDTClick: function(event)
	{
		var anchorElement = Event.findElement(event, 'a');

		// Note: the below line can be commented out if desired -- it'll display the anchor element's onclick function
		// if (typeof console != 'undefined') console.log("anchorElement.onclick is " + anchorElement.onclick.toString());

		// Grab the targetID based on the anchors href attribute
		// For example, if it's set to "#how-do-i-park-my-cat",
		// the targetID would be "how-do-i-park-my-car"

		/*
			stringVariable.match returns an array.
			- the first element of the array is the full match
			- then the subsequent parenthesized bits are
				listed as the following array elements

			(so, in this case, [1] gives us the first parenthesized
			bit, avoiding the preceding octothrope at the beginning
			of the string)
		*/
		var targetID = anchorElement.href.match(/#(.*)/)[1];

		/* If no valid target ID was found, exit */
		if ((!targetID) || (!targetID.length))
		{
			return;
		}

		var firstDDElement = $(targetID);

		// If no matching element was found exit
		if (!firstDDElement) return;

		/* Collect any other dd elements, just in case */

		var ddElements = [firstDDElement]; // initialize
		var nextSibling = firstDDElement.next();

		while ((nextSibling) && (nextSibling.tagName.toLowerCase() == "dd"))
		{
			ddElements.push(nextSibling);
			nextSibling = nextSibling.next();
		}

		if ( isVisible( firstDDElement ))
		{
			ddElements.each(function(ddElement) {
				Effect.SlideUp(ddElement, {afterFinish: function(effectObject) {
						effectObject.element.setAttribute('tabindex', '0');

						var tempFunction = this._findClickHandlerForAnchor(anchorElement);

						if (tempFunction)
						{
							tempFunction(event);
						}

						// prevent memory leaks
						anchorElement = null;
						tempFunction = null;
					}.bind(this)
				}); // end of Effect.SlideUp
			}.bind(this));	// end of ddElements.each
		}
		else
		{
			/* Set the ddElement(s) to tabindex of -1
				and then focus it/them, for accessibility */
			ddElements.each(function(ddElement) {
				Effect.BlindDown(ddElement, {afterFinish: function(effectObject) {
						effectObject.element.setAttribute('tabindex', '-1');

						/*
							"The timeout [on .focus()] is necessary in both IE and Firefox 1.5, to prevent
							scripts from doing strange unexpected things as the user clicks on buttons and other controls."

							http://developer.mozilla.org/en/docs/Key-navigable_custom_DHTML_widgets#Use_onfocus_to_track_the_current_focus
						*/
						window.setTimeout(function() {
							effectObject.element.focus();
						}, 0);

						/* If need be -- which currently isn't the case -- Omniture's s_objectID
							could be set here. In the currently implementation, though,
							this isn't necessary since the click handler for items
							such as these is set up after the anchors have been created.
							In other words, these anchors act like any other anchor. */

						var tempFunction = this._findClickHandlerForAnchor(anchorElement);

						if (tempFunction)
						{
							tempFunction(event);
						}

						// prevent memory leaks
						anchorElement = null;
						tempFunction = null;

					}.bind(this) // end of afterFinish function
				}); // end of Effect.BlindDown
			}.bind(this)); // end of ddElements.each

		}

		Event.stop(event); // prevent the link from firing
	}
}




/*
	JSTarget function by Roger Johansson:
	http://www.456bereastreet.com/archive/200610/opening_new_windows_with_javascript_version_12/

	This sets anchor tags hich have rel="external" to open in a new window.
*/
var JSTarget = {
	init: function(att,val,warning) {
		if (document.getElementById && document.createElement && document.appendChild) {
			var strAtt = ((typeof att == 'undefined') || (att == null)) ? 'class' : att;
			var strVal = ((typeof val == 'undefined') || (val == null)) ? 'non-html' : val;
			var strWarning = ((typeof warning == 'undefined') || (warning == null)) ? ' (opens in a new window)' : warning;
			var oWarning;
			var arrLinks = document.getElementsByTagName('a');
			var oLink;
			var oRegExp = new RegExp("(^|\\s)" + strVal + "(\\s|$)");
			for (var i = 0, len=arrLinks.length; i < len; i++) {
				oLink = arrLinks[i];
				if ((strAtt == 'class') && (oRegExp.test(oLink.className)) || (oRegExp.test(oLink.getAttribute(strAtt)))) {
 					/* Set the link's title attribute to the warning text */
					oLink.title = oLink.title + warning;
					oLink.onclick = JSTarget.openWin;
				}
			}
			oWarning = null;
		}
	},
	openWin: function(e) {
		var event = (!e) ? window.event : e;
		if (event.shiftKey || event.altKey || event.ctrlKey || event.metaKey) return true;
		else {
		    var oWin = window.open(this.getAttribute('href'), '_blank');
			if (oWin) {
				if (oWin.focus) oWin.focus();
				return false;
			}
			oWin = null;
			return true;
		}
	},
	/*
	addEvent function from http://www.quirksmode.org/blog/archives/2005/10/_and_the_winner_1.html
	*/
	addEvent: function(obj, type, fn) {
		if (obj.addEventListener)
			obj.addEventListener(type, fn, false);
		else if (obj.attachEvent) {
			obj["e"+type+fn] = fn;
			obj[type+fn] = function() {obj["e"+type+fn]( window.event );}
			obj.attachEvent("on"+type, obj[type+fn]);
		}
	}
};
JSTarget.addEvent(window, 'load', function(){JSTarget.init("rel","external"," (external website, opens in a new window)");});


var Rules = {

	/* This hides all the dd elements by default */
	'body div#content dl.faq dd, body div#primary-content dl.faq dd, body div#primary-content dl.projectHistory dd' : function(element) {

		Element.hide(element);
	},

	'body div#content dl.faq dt, body div#primary-content dl.faq dt, div#primary-content dl.projectHistory dt' : function(element) {

		/* Create an anchor element to go with each dt element, then move the dt's child nodes
			to that anchor element */

		var anchorElement = document.createElement('a');

		var targetID = ''; // initialize

		/* Grab all the text-node children from the dt element and put them
			inside the anchor element. (element.appendChild() movoes a given
			element if the element-to-be-appended already exists in the DOM) */

		for (var i = 0, len = element.childNodes.length; i < len; i++)
		{
			if (element.childNodes[i].nodeType == 3)
			{
				targetID = targetID + element.childNodes[i].nodeValue;
			}

			anchorElement.appendChild( element.childNodes[i] );
		}

		/* Convert the string to lowercase, replace all whitespace bits with
			hyphens, and remove any non-alphanumeric / non-hyphen characters */
		targetID = targetID.toLowerCase().replace(/\s+/g,'-').match(/[a-zA-Z]+[a-zA-Z0-9\-]*/);

		anchorElement.href = "#" + targetID;

		// Then append the anchor to the dt element
		element.appendChild(anchorElement);

		/* Add this guy to the cached-anchors list. This list is used
			so that the onclick even handlers which Omniture adds
			can be manually called later.

			Specifically, Omniture's onclick bits tend to sludgify
			the Script.aculo.us animation used for those guys. So,
			the onlick bits are pulled off and then manually
			run after the animation on given item has compelted.
		*/
		Common.cachedAnchorList.push(
			{
				element: anchorElement,
				onclickFunction: null
			}
		);

		/* Then, find the target DD element and set that ID for it */

		var ddElement = element.next(); // initialize

		while ((ddElement) && (ddElement.tagName.toLowerCase() != "dd"))
		{
			ddElement = ddElement.next();
		}

		// If no valid dd element was found, exit
		if (!ddElement) return;

		// Set its ID to match the URL-fragment applied to the anchor earlier
		ddElement.setAttribute('id', targetID);

		Event.observe(anchorElement, 'click', Common.processDTClick.bindAsEventListener(Common));

		// prevent memory leaks
		anchorElement = null;
		element = null;
	}
};

/*
	Event.observe is overloaded to fire as soon as the DOM is ready,
	via the domready-onload.js script.

	http://www.agileweb.org/articles/2006/06/16/speeding-up-onload
*/
DOMReady.addFunctionToQueue( function()
	{
		OmnitureAssistant.runAssistant();

		Common.addHelperElements();

		EventSelectors.start(Rules);

		// This sets the click handlers for anchors with "#"
		// to define s_objectID (for Omniture)
		OmnitureAssistant.setClickHandlersForSObjectID();
	});

/* The processCachedAnchorClickHandlers() function is triggered by window.onload
	since it takes a few milliseconds for Omniture to add its event handlers anyway */
Event.observe(window, 'load', Common.processCachedAnchorClickHandlers.bind(Common) );

function hideStatusBox()
{
	var mydiv = document.getElementById("account-closure-main");
	var agreediv = document.getElementById("agreement-terms");
   if (mydiv != null)
   {
      //alert("Sorry can't find your div");
	  mydiv.style.visibility="hidden";

      //return;
   }
   if(agreediv !=null){
	   agreediv.style.visibility="visible";
   }
   //div found
   //mydiv.style.visibility="hidden";
   //return;
}

function launch() {
open('totd/tipofday.htm', 'tipofday', 'resizable=yes, toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, width=500, height=300')
}
function showTerms()
{
	document.getElementById("yellow").style.display = "none";
	document.getElementById("account-closure-main").style.display = "";
	document.getElementById("agreement").style.display = "";
	resizeText(-1);
}

function showAgreementTermBox()
{
	document.getElementById("agreement").style.display = "";
	document.getElementById("agreement-image").style.display = "";
	document.getElementById("agreement-image-text").style.display = "";
	resizeText(-1);
}
function showrefund(){

	resizeText(-1);
	document.getElementById("agreement").style.display = "none";
	document.getElementById("agreement-image").style.display = "none";
	document.getElementById("agreement-image-text").style.display = "none";
	document.getElementById("refund").style.display = "";

}
function showNonRefund(){
	document.getElementById("agreement").style.display = "";
	document.getElementById("agreement-image").style.display = "none";
	document.getElementById("agreement-image-text").style.display = "none";
	document.getElementById("refund").style.display = "none";
	resizeText(-1);
}
function cancel(){
	document.getElementById("account-closure-main").style.display = "none";
	document.getElementById("yellow").style.display = "none";
	document.getElementById("yellow-text").style.display = "none";
	resizeText(-1);

}

function resizeText(multiplier) {

	 	  document.body.style.fontSize =  "75%";
	}

function doPaypageSubmit( submitCallBack, timeoutOnLitle) {
	var litleRequest = {
	        "paypageId": document.getElementById("request$paypageId").value,
	        "reportGroup": document.getElementById("request$reportGroup").value,
	        "orderId": document.getElementById("request$orderId").value,
	        "id": document.getElementById("request$merchantTxnId").value,
	        "url": document.getElementById("pagePageRequestUrl").value
	};


	var formFields = {
			"accountNum" :document.getElementById('card-number'),
			"cvv2" :document.getElementById('cvv'),
			"paypageRegistrationId":document.getElementById('response$paypageRegistrationId'),
			"bin" :document.getElementById('response$bin')
	};

	var onErrorAfterLitle = function(response){
		if(response.response =='871')
	    {
	    alert("Invalid card number. Check and retry. (Not Mod10)");
	    }
	    else if(response.response=='872')
	    {
	    alert("Invalid card number. Check and retry. (Too short)");
	    }
	    else if(response.response=='873')
	    {
	    alert("Invalid card number. Check and retry. (Too long)");
	    }
	    else if(response.response=='874')
	    {
	    alert("Invalid card number. Check and retry. (Not a number)");
	    }
	    else if(response.response=='875')
	    {
	    alert("We are experiencing technical difficulties. Please try again later or call 555 - 555 - 1212");
	    } else if(response.response=='876')
	    {
	    alert("Invalidcardnumber.Checkandretry.(FailurefromServer)");}
	    else if(response.response=='881')
	    {
	           alert("Invalidcardvalidationcode.Checkandretry.(Notanumber)");
	    }
	    else if(response.response=='882')
	    {
	           alert("Invalidcardvalidationcode.Checkandretry.(Tooshort)");
	    }
	    else if(response.response=='883')
	    {
	           alert("Invalidcardvalidationode.Checkandretry.(Toolong)");
	    }
	    else if(response.response=='889')
	    {
	           alert("Weareexperiencingtechnicaldifficulties.Pleasetryagainlaterorcall555-555-1212");
	    }
	    return false;
	};
	new LitlePayPage().sendToLitle(litleRequest, formFields, submitCallBack,onErrorAfterLitle, timeoutOnLitle, 5000);
}