/*
	event:Selectors properties for the Account areas.

	(event:Selectors - http://encytemedia.com/event-selectors/)
*/



/* This makes use of namespacing via object literals:
	http://www.wait-till-i.com/index.php?p=239 */

AccountSection = {

	/* When someone clicks the logo or one of the footer linkss within the signup process,
		he or she is presented with a confirmation message first. */
	//SIGNUP_LEAVING_CONFIRMATION_TEXT : 'If you go back to the home page, you will have to start over with the sign-up process. Are you sure you want to return to the home page?',
        SIGNUP_LEAVING_CONFIRMATION_TEXT : 'Are you sure you want to abandon the sign-up process?',

	FIRST_NAME_TEXT : 'First Name:',
	LAST_NAME_TEXT : 'Last Name:',
	CONTACT_FIRST_NAME_TEXT : 'Contact First Name:',
	CONTACT_LAST_NAME_TEXT : 'Contact Last Name:',
        DRIVER_LICENSE_TEXT: "Driver's License Number:",
        COMPANY_DRIVER_LICENSE_TEXT: "Driver's License Number:",
        NOTATION_TEXT: '* Required fields.',
        COMPANY_NOTATION_TEXT: "* Required fields. ** Either Tax ID Number -or- Driver's License Number is required",

	EXPANDED_CLASSNAME : 'expanded',
	COLLAPSED_CLASSNAME : 'collapsed',

	INVOICE_SUMMARY_CLASSNAME : 'invoice-summary',

	ONLINE_DISCOUNT_CLASSNAME : 'online-discount',
	WAIVER_CLASSNAME : 'fee-waiver',
	ADJUSTMENT_CLASSNAME : 'fee-adjustment',
	TOTALS_CLASSNAME : 'totals',

	FORM_SIGN_UP_ID : 'sign-up',

	LABEL_FIRST_NAME_ID: 'for-first-name',
	LABEL_LAST_NAME_ID: 'for-last-name',
        LABEL_DRIVER_LICENSE_ID: 'for_dl_number',
        NOTATION_ID: 'notation',

	INPUT_ELEMENT_NAME_ON_CREDIT_CARD_ID : 'name-on-credit-card',
	INPUT_ELEMENT_AGREE_TO_LICENSE_AGREEMENT_ID : 'agree-to-license-agreement',
	INPUT_ELEMENT_DELETE_THIS_CONTACT_CLASSNAME : 'delete-this-contact',

	INPUT_ELEMENT_PERSONAL_ID : 'personal',
	INPUT_ELEMENT_BUSINESS_ID : 'business',

	INPUT_ELEMENT_NON_US_ADDRESS : 'non-us-address',
	INPUT_ELEMENT_ADDRESS_LINE_1_ID : 'address-line-1',

	PAYMENTS_TABLE_CLASSNAME: 'payments',

	SELECT_ELEMENT_PAY_IN_FULL_VALUE : 'PAY_IN_FULL',
	SELECT_ELEMENT_SPECIFY_AMOUNT_VALUE : 'SPECIFY_AMOUNT',
	SELECT_ELEMENT_DONT_PAY_VALUE : 'DONT_PAY',
	SELECT_ELEMENT_ADJUST_VALUE : 'ADJUST',
	SELECT_ELEMENT_WAIVE_VALUE : 'WAIVE',

	PAY_ALL_ADJUST_ID : 'pay-all-adjust',
	PAY_ALL_WAIVE_ID : 'pay-all-waive',
	PAY_ALL_FULL_ID : 'pay-all-full',

	FIELDSET_ELEMENT_ADD_TO_EZ_ACCOUNT_CLASSNAME: 'add-to-ez-account-fields',
	PARAGRAPH_TOTAL_PAYMENT_CLASSNAME: 'total-payment',

	SELECT_ELEMENT_COUNTRY_ID : 'country',

	BILLING_TYPE_CHOICES_CLASSNAME : 'choices',
	BILLING_TYPE_CREDIT_CARD_ID : 'credit-card',
	BILLING_TYPE_WITHDRAW_FUNDS_ID : 'withdraw-funds',
	BILLING_TYPE_COMMON_FIELDS_ID : 'common',
	BILLING_TYPE_FORM_ACTIONS_CLASSNAME : 'form-actions',

	PRIMARY_CARD_FOR_REBILLING_PARAGRAPH_ID: 'primary-card-for-rebilling-field',
	UPDATE_BILLING_ADDRESS_FIELD_PARAGRAPH_ID: 'update-billing-address-field',

	INPUT_ELEMENT_NAME_ON_ALTERNATE_CREDIT_CARD : 'name-on-alternate-credit-card',

	ALTERNATE_CREDIT_CARD_FIELDS_ID : 'alternate-credit-card-fields',
	/**	this requirement was deleted. I've kept the code here in case it comes back - 11/02/2006 **/
	/* ALTERNATE_EFT_ACCOUNT_FIELDS : 'alternate-eft-account-fields', */

	ADD_CONTACTS_AUTHORIZED_CONTACTS_ID : 'authorized-contacts',

	COMPANY_NAME_ITEM_CLASSNAME : 'company-name-item',
	TAX_ID_NUMBER_ITEM_CLASSNAME : 'tax-id-number-item',
	DRIVERS_LICENSE_NUMBER_ITEM_CLASSNAME : 'drivers-license-number-item',

	FIELDSET_ELEMENT_BILLING_ADDRESS_FIELDS : 'billing-address-fields',
	DOMESTIC_ADDRESS_ITEM_CLASSNAME : 'domestic',
	NON_US_ADDRESS_ITEM_CLASSNAME : 'non-us',

	COUNTRY_LIST_COUNTRY_DT_ID : 'country-dt',
	COUNTRY_LIST_COUNTRY_DD_ID : 'country-dd',

	FIRST_ADDRESS_LINE_DT_ID : 'address-line-1-dt',
	FIRST_ADDRESS_LINE_DD_ID : 'address-line-1-dd',

	FIRST_DT_DD_PAIR_CLASSNAME : 'first-dt-dd-pair',

	NORMALLY_PRINT_BUT_NOT_SCREEN_CLASSNAME : 'normally-print-but-not-screen',
	NORMALLY_SCREEN_BUT_NOT_PRINT_CLASSNAME : 'normally-screen-but-not-print',

	ALL_MEDIA_TYPES_EXCEPT_FOR_SCREEN_REGEX : /((all|aural|braille|embossed|handheld|print|projection|tty|tv),*)+/i,
	ALL_MEDIA_TYPES_EXCEPT_FOR_PRINT_REGEX : /((all|aural|braille|embossed|handheld|projection|screen|tty|tv),*)+/i,

	PRINT_ANCHOR_CLASSNAME : 'print',
	PRINT_PREVIEW_ENABLE_TEXT : 'print preview',
	PRINT_PREVIEW_DISABLE_TEXT : 'turn off print preview',

	HELP_CLASSNAME : 'help',
	CURRENCY_CLASSNAME : 'currency',

	/* Sets the "Account Information" area to Personal mode */
	setAsPersonalMode: function()
	{
		var companyNameItems = document.getElementsByClassName(AccountSection.COMPANY_NAME_ITEM_CLASSNAME, AccountSection.FORM_SIGN_UP_ID);

		companyNameItems.each( function(element) {
				Element.hide(element);
			});


		var taxIDNumberItems = document.getElementsByClassName(AccountSection.TAX_ID_NUMBER_ITEM_CLASSNAME, AccountSection.FORM_SIGN_UP_ID);

		taxIDNumberItems.each( function(element) {
				Element.hide(element);
			});

		var driversLicenseNumberItems = document.getElementsByClassName(AccountSection.DRIVERS_LICENSE_NUMBER_ITEM_CLASSNAME, AccountSection.FORM_SIGN_UP_ID);

		driversLicenseNumberItems.each( function(element) {
				Element.removeClassName(element, 'in-business-mode');
			});

		/* Set the labels for "First Name" and "Last Name" to
			"First Name" and "Last Name" (as opposed to "Contact First
			 Name" and "Contact Last Name", the settings for Business mode) */

		var firstNameLabel = $(AccountSection.LABEL_FIRST_NAME_ID);
		var lastNameLabel = $(AccountSection.LABEL_LAST_NAME_ID);
                var dlLabel = $(AccountSection.LABEL_DRIVER_LICENSE_ID);
                var notation = $(AccountSection.NOTATION_ID);

		// Loop through the child nodes of the firstNameLabel element and change its text
		for (var i = 0, len = firstNameLabel.childNodes.length; i < len; i++)
		{
			var currentNode = firstNameLabel.childNodes[i];

			// "3" = text node
			if ((currentNode.nodeType == 3) && (currentNode.nodeValue.indexOf(AccountSection.CONTACT_FIRST_NAME_TEXT) != -1))
			{
				currentNode.nodeValue = AccountSection.FIRST_NAME_TEXT;
			}
		}

		// Loop through the child nodes of the lastNameLabel element and change its text
		for (var i = 0, len = lastNameLabel.childNodes.length; i < len; i++)
		{
			var currentNode = lastNameLabel.childNodes[i];

			// "3" = text node
			if ((currentNode.nodeType == 3) && (currentNode.nodeValue.indexOf(AccountSection.CONTACT_LAST_NAME_TEXT) != -1))
			{
				currentNode.nodeValue = AccountSection.LAST_NAME_TEXT;
			}
		}

                for (var i = 0, len = notation.childNodes.length; i < len; i++)
		{
			var currentNode = notation.childNodes[i];

			// "3" = text node
			if ((currentNode.nodeType == 3) && (currentNode.nodeValue.indexOf('Required') != -1))
			{
                                currentNode.nodeValue = AccountSection.NOTATION_TEXT;
			}
		}

                for (var i = 0, len = dlLabel.childNodes.length; i < len; i++)
		{

			var currentNode = dlLabel.childNodes[i];

			// "3" = text node
			if ((currentNode.nodeType == 3) && (currentNode.nodeValue.indexOf('Driver') != -1))
			{
                                currentNode.nodeValue = AccountSection.DRIVER_LICENSE_TEXT;
			}
		}

                document.getElementById("driver-lic-prefix").innerHTML="*";
                document.getElementById("company-name").value = "";
                document.getElementById("tax-id-number").value= "";
                document.getElementById("first-name").focus();
	},

	/* Sets the "Account Information" area to Business mode */
	setAsBusinessMode: function()
	{
		var companyNameItems = document.getElementsByClassName(AccountSection.COMPANY_NAME_ITEM_CLASSNAME, AccountSection.FORM_SIGN_UP_ID);

		companyNameItems.each( function(element) {
				Element.show(element);
			});


		var taxIDNumberItems = document.getElementsByClassName(AccountSection.TAX_ID_NUMBER_ITEM_CLASSNAME, AccountSection.FORM_SIGN_UP_ID);

		taxIDNumberItems.each( function(element) {
				Element.show(element);
			});

		var driversLicenseNumberItems = document.getElementsByClassName(AccountSection.DRIVERS_LICENSE_NUMBER_ITEM_CLASSNAME, AccountSection.FORM_SIGN_UP_ID);

		driversLicenseNumberItems.each( function(element) {
				Element.addClassName(element, 'in-business-mode');
			});

		/* Set the labels for "First Name" and "Last Name" to
			"Contact First Name" and "Contact Last Name", respectively */

		var firstNameLabel = $(AccountSection.LABEL_FIRST_NAME_ID);
		var lastNameLabel = $(AccountSection.LABEL_LAST_NAME_ID);
                var dlLabel = $(AccountSection.LABEL_DRIVER_LICENSE_ID);
                var notation = $(AccountSection.NOTATION_ID);

		// Loop through the child nodes of the firstNameLabel element and change its text
		for (var i = 0, len = firstNameLabel.childNodes.length; i < len; i++)
		{
			var currentNode = firstNameLabel.childNodes[i];

			// "3" = text node
			if ((currentNode.nodeType == 3) && (currentNode.nodeValue.indexOf(AccountSection.FIRST_NAME_TEXT) != -1))
			{
				currentNode.nodeValue = AccountSection.CONTACT_FIRST_NAME_TEXT;
			}
		}

		// Loop through the child nodes of the lastNameLabel element and change its text
		for (var i = 0, len = lastNameLabel.childNodes.length; i < len; i++)
		{
			var currentNode = lastNameLabel.childNodes[i];

			// "3" = text node
			if ((currentNode.nodeType == 3) && (currentNode.nodeValue.indexOf(AccountSection.LAST_NAME_TEXT) != -1))
			{
				currentNode.nodeValue = AccountSection.CONTACT_LAST_NAME_TEXT;
			}
		}

                for (var i = 0, len = notation.childNodes.length; i < len; i++)
		{
			var currentNode = notation.childNodes[i];

			// "3" = text node
			if ((currentNode.nodeType == 3) && (currentNode.nodeValue.indexOf('Required') != -1))
			{
                                currentNode.nodeValue = AccountSection.COMPANY_NOTATION_TEXT;
			}
		}

                for (var i = 0, len = dlLabel.childNodes.length; i < len; i++)
		{
			var currentNode = dlLabel.childNodes[i];

			// "3" = text node
			if ((currentNode.nodeType == 3) && (currentNode.nodeValue.indexOf('Driver') != -1))
			{
                                currentNode.nodeValue = AccountSection.COMPANY_DRIVER_LICENSE_TEXT;
			}
		}

                document.getElementById("driver-lic-prefix").innerHTML="**";
                document.getElementById("company-name").focus();

	},

	/* Sets an address area to non-US mode */
	setAsNonUSAddress: function()
	{
		var nonUSAddressItems = document.getElementsByClassName(AccountSection.NON_US_ADDRESS_ITEM_CLASSNAME, AccountSection.FORM_SIGN_UP_ID);

		nonUSAddressItems.each( function(element) {
				Element.show(element);
			});

		var domesticAddressItems = document.getElementsByClassName(AccountSection.DOMESTIC_ADDRESS_ITEM_CLASSNAME, AccountSection.FORM_SIGN_UP_ID);

		domesticAddressItems.each( function(element) {
				Element.hide(element);
			});

		// Next up, clean up some classes since the first-visible-item
		// in the definition list may now be different
		$(AccountSection.COUNTRY_LIST_COUNTRY_DT_ID ).addClassName(AccountSection.FIRST_DT_DD_PAIR_CLASSNAME);
		$(AccountSection.COUNTRY_LIST_COUNTRY_DD_ID).addClassName(AccountSection.FIRST_DT_DD_PAIR_CLASSNAME);

		$(AccountSection.FIRST_ADDRESS_LINE_DT_ID).removeClassName(AccountSection.FIRST_DT_DD_PAIR_CLASSNAME);
		$(AccountSection.FIRST_ADDRESS_LINE_DD_ID).removeClassName(AccountSection.FIRST_DT_DD_PAIR_CLASSNAME);

	},

	/* Sets an address area to domestic mode */
	setAsDomesticAddress: function()
	{
		var nonUSAddressItems = document.getElementsByClassName(AccountSection.NON_US_ADDRESS_ITEM_CLASSNAME, AccountSection.FORM_SIGN_UP_ID);

		nonUSAddressItems.each( function(element) {
				Element.hide(element);
			});

		var domesticAddressItems = document.getElementsByClassName(AccountSection.DOMESTIC_ADDRESS_ITEM_CLASSNAME, AccountSection.FORM_SIGN_UP_ID);

		domesticAddressItems.each( function(element) {
				Element.show(element);
			});

		// Next up, clean up some classes since the first-visible-item
		// in the definition list may now be different

		$(AccountSection.COUNTRY_LIST_COUNTRY_DT_ID ).removeClassName(AccountSection.FIRST_DT_DD_PAIR_CLASSNAME);
		$(AccountSection.COUNTRY_LIST_COUNTRY_DD_ID).removeClassName(AccountSection.FIRST_DT_DD_PAIR_CLASSNAME);

		$(AccountSection.FIRST_ADDRESS_LINE_DT_ID).addClassName(AccountSection.FIRST_DT_DD_PAIR_CLASSNAME);
		$(AccountSection.FIRST_ADDRESS_LINE_DD_ID).addClassName(AccountSection.FIRST_DT_DD_PAIR_CLASSNAME);

	},

	/* This set the address fields to "Domestic" or "NonUS"
		modes according to the "isNonUSAddress" parameter is sent in */
	setAsDomesticOrNonUSAddress : function(isNonUSAddress)
	{
		switch (isNonUSAddress)
		{
			case true:
				/* Sets an address area to non-US mode */
				AccountSection.setAsNonUSAddress();
				break;

			case false:
				/* Sets an address area to domestic mode */
				AccountSection.setAsDomesticAddress();
				break;
		}

	},

	/* Displays the adjacent help if the element is checked */
	showAdjacentHelp: function(element)
	{
		if (element.checked)
		{
			var showHelp = true;
		}
		else
		{
			var showHelp = false;
		}

		/* Walk up the tree until the parent dd element is found */

		var parentElement = element; // initialize;


		// Walk up the tree until you run into the parent dd item (or the body element)
		while ((parentElement.tagName.toLowerCase() != "dd") && (parentElement.tagName.toLowerCase() != "body"))
		{
			parentElement = parentElement.parentNode;
		}

		// If it walked all the way up the tree and found no appropriate parent elements, exit
		if (parentElement.tagName.toLowerCase() == "body") return;

		// Then, grab all the help elements under that parent element
		var adjacentHelpElements = document.getElementsByClassName(AccountSection.HELP_CLASSNAME, parentElement);

		switch (showHelp)
		{
			case true:
				adjacentHelpElements.each( function(helpElement) {
					Element.show(helpElement);
				});
				break;

			case false:
				adjacentHelpElements.each( function(helpElement) {
					Element.hide(helpElement);
				});
				break;
		}

	},

	setAsTemporaryLicensePlate : function(element)
	{
		if (element.checked)
		{
			var setAsTemp = true;
		}
		else
		{
			var setAsTemp = false;
		}

		/* Walk up the tree until the parent dd element is found */

		var parentElement = element; // initialize;


		// Walk up the tree until you run into the parent dd item (or the body element)
		while ((parentElement.tagName.toLowerCase() != "dd") && (parentElement.tagName.toLowerCase() != "body"))
		{
			parentElement = parentElement.parentNode;
		}

		// If it walked all the way up the tree and found no appropriate parent elements, exit
		if (parentElement.tagName.toLowerCase() == "body") return;

		// Then, grab all the text fields under that parent element
		var adjacentTextfields = document.getElementsByClassName('textfield', parentElement);

		switch (setAsTemp)
		{
			case true:
				adjacentTextfields.each( function(textField) {
					textField.value = "TEMP";
					textField.disabled = "disabled";

					// The disabled field also gets the class "disabled"
					// to homogenize differences between IE and Real Browsers
					textField.addClassName("disabled");
                                        var divElement = document.getElementById('pbpDiv');
                                        if (divElement!=null)
                                        {
                                            divElement.style.display="none";
                                            document.tagRequestForm.pbpTag.checked = '';
                                            document.tagRequestForm.pbpStart.value='';
                                            document.tagRequestForm.pbpStart.value='';
                                        }
				});
				break;

			case false:
				adjacentTextfields.each( function(textField) {
					if ( $F(textField)=="TEMP" )
					{
						textField.value = "";
					}
					textField.disabled = "";
					textField.removeClassName("disabled");
                                        var divElement = document.getElementById('pbpDiv');
                                        if (divElement!=null && !document.tagRequestForm.pbpTagsExist)
                                        {
                                            divElement.style.display="";
                                        }
				});
				break;
		}

	},

	/* This sets the billing type on the page of the same
		name, either to credit-card or a withdrawl of funds */
	setBillingType: function(billingType)
	{
//		/* Note: The accessibility-target element within the page
//			(in this case, "credit-card") must /not/ have the same
//			identifyer as what's added to the history (in this case,
//			"credit-card-choice" or "withdraw-funds-choice"). Otherwise,
//			IE gets a little confused when going back. */
//
//		switch(billingType)
//		{
//			case 'credit-card-choice':
//				Element.show(AccountSection.BILLING_TYPE_CREDIT_CARD_ID);
//				Element.hide(AccountSection.BILLING_TYPE_WITHDRAW_FUNDS_ID);
//
//				// Then show the common sections
//				var commonSections = $A( document.getElementsByClassName(AccountSection.BILLING_TYPE_COMMON_FIELDS_ID, 'primary-content') );
//
//				commonSections.each(function (sectionToShow) {
//					Element.show(sectionToShow);
//				});
//
//				// Also show the form buttons
//				Element.show( document.getElementsByClassName(AccountSection.BILLING_TYPE_FORM_ACTIONS_CLASSNAME, AccountSection.FORM_SIGN_UP_ID).first() );
//
//				// Finally, hide the list of choices
//				Element.hide( document.getElementsByClassName(AccountSection.BILLING_TYPE_CHOICES_CLASSNAME, 'primary-content').first() );
//
//				// And focus the "name" field
//				Field.focus(AccountSection.INPUT_ELEMENT_NAME_ON_CREDIT_CARD_ID);
//
//				break;
//
//			case 'withdraw-funds-choice':
//				Element.show(AccountSection.BILLING_TYPE_WITHDRAW_FUNDS_ID);
//				Element.hide(AccountSection.BILLING_TYPE_CREDIT_CARD_ID);
//
//				// Then show the common sections
//				var commonSections = $A( document.getElementsByClassName(AccountSection.BILLING_TYPE_COMMON_FIELDS_ID, 'primary-content') );
//
//				commonSections.each(function (sectionToShow) {
//					Element.show(sectionToShow);
//				});
//
//				// Also show the form buttons
//				Element.show( document.getElementsByClassName(AccountSection.BILLING_TYPE_FORM_ACTIONS_CLASSNAME, AccountSection.FORM_SIGN_UP_ID).first() );
//
//				// Finally, hide the list of choices
//				Element.hide( document.getElementsByClassName(AccountSection.BILLING_TYPE_CHOICES_CLASSNAME, 'primary-content').first() );
//
//				// And focus the "personal" field (within the personal/business set)
//
//				/*
//					(This is used rather than a literal Field.focus() call so that it'll
//					focus the first radio button which is checked, not necessarily the
//					first radio button that it comes across)
//				*/
//				AccountSection.focusFirstFormField( $('content-container') );
//
//				break;
//
//			default:
//				/* By default, hide both of them */
//				Element.hide(AccountSection.BILLING_TYPE_WITHDRAW_FUNDS_ID);
//				Element.hide(AccountSection.BILLING_TYPE_CREDIT_CARD_ID);
//
//				// Then hide the common sections
//				var commonSections = $A( document.getElementsByClassName(AccountSection.BILLING_TYPE_COMMON_FIELDS_ID, 'primary-content') );
//
//				commonSections.each(function (sectionToHide) {
//					Element.hide(sectionToHide);
//				});
//
//				// Also hide the form buttons
//				Element.hide( document.getElementsByClassName(AccountSection.BILLING_TYPE_FORM_ACTIONS_CLASSNAME, AccountSection.FORM_SIGN_UP_ID).first() );
//
//				// Finally, show the list of choices
//				Element.show( document.getElementsByClassName(AccountSection.BILLING_TYPE_CHOICES_CLASSNAME, 'primary-content').first() );
//		}

	},

	/* Checks if there's a legal-checkbox on the page and disables the
		form if it's not checked */
	checkForLegalAgreement: function(event)
	{
		if (!event) var event = window.event;

		var legalAgreementCheckbox = $(AccountSection.INPUT_ELEMENT_AGREE_TO_LICENSE_AGREEMENT_ID);

		if (legalAgreementCheckbox)
		{

			switch (legalAgreementCheckbox.checked)
			{
				case false:
						Event.stop(event);
						alert("Please confirm your acceptance of the EZ agreement.");
						Field.focus(AccountSection.INPUT_ELEMENT_AGREE_TO_LICENSE_AGREEMENT_ID);
						return false;
						break;

//				case true:
//						// These bits are temporary, for the sake of the prototypes
//						// IRL, the form would just submit normally
//						document.location.href='page-05-confirmation-page.shtml';
//						Event.stop(event);
//						return false;
//						break;

			} // end of switch statement
		}

	},

	/* This takes a string, chops off the number from the end,
		increments that, and appends the new number.

		If the /optional/ parameter forcedValue is sent in,
		the node is forced to that forcedValue instead of
		literally incrementing */
	incrementNode : function(node, forcedValue)
	{
		// Match for a group of numbers at the end of the string
		var numberRegEx = /[0-9]+/;

		var numberMatches = numberRegEx.exec(node);

		// If the attribute doesn't have numbers at the end,
		// just return it unmodified
		if (numberMatches == null) return node;

		// If it got this far, then there's a valid number
		// on which to work...

		// If forcedValue was sent in, use that for newValue; or, if not
		// set newValue to the increment-y value
		// http://www.vivabit.com/bollocks/2006/01/09/cheeky-tip-default-function-arguments-in-javascript

		var newValue = forcedValue || parseInt(numberMatches[0]) + 1;

		return node.replace(numberRegEx, newValue);

	},

	/* This is used for the deleteThisField / addAnotherField functions.
		It goes through all the list items in an unordered list and
		makes sure that they're sequential.

		For example, if this list is sent in:

			* 1
			* 2
			* 4
			* 5

		The list is renumbered to this:

			* 1
			* 2
			* 3
			* 4
	*/
	renumberListItems : function(unorderedList)
	{
		unorderedList = $(unorderedList);

		/* Walk up the tree until the unordered list is found
			(to allow for the function to accept any child
			element of the list and still work correctly) */

		var unorderedList = unorderedList.walkUpUntil('ul');

		// If no parent list item was found, exit
		if (unorderedList === null) return;

		// Count the number of list items to find the renumbering-boundaries

		var listItems = $A( unorderedList.getElementsByTagName('li') );
		var numberOfListItems = listItems.length;

		// If there're no list items, then something has gone terribly awry,
		// so exit
		if (numberOfListItems < 1 ) return;

		// Then loop through the list items and do the renumbering

		for (listItemIndex = 1; listItemIndex <= numberOfListItems; listItemIndex++)
		{
			// "1" is subtracted here since arrays are 0-indexed while
			// the counter "listItemIndex" is 1-indexed
			var currentListItem = listItems[listItemIndex - 1];

			// Loop through the child nodes of the currentListItem and
			// update the "for", "id" and "name" attributes in there
			for (var i = 0, len = currentListItem.childNodes.length; i < len; i++)
			{
				var currentNode = currentListItem.childNodes[i];

				// "1" = element node
				if (currentNode.nodeType == 1)
				{

					if (currentNode.tagName.toLowerCase() == 'label')
					{
						/* If it's a label, update it's 'for' attribute and the label text */

						// Loop through the child nodes of the element and update its text
						for (var labelIndex = 0, labelLength = currentNode.childNodes.length; labelIndex < labelLength; labelIndex++)
						{
							var currentChild = currentNode.childNodes[labelIndex];

							// "3" = text node
							if (currentChild.nodeType == 3)
							{
								currentChild.nodeValue = AccountSection.incrementNode(currentChild.nodeValue, listItemIndex);
							}
						}

						/* Stupid IE doesn't support hasAttribute,
							hence the hoop-jumping here and elsewhere where
							hasAttribute would normally be used:
							http://www.quirksmode.org/dom/w3c_core.html#attributes */

						var forAttribute = currentNode.getAttribute('for');

						if ( (forAttribute) && (forAttribute.length > 0) )
						{
							// the syntax on this line is a bit more involved than the others since "for"
							// is a reserved keyword and can't be accessed by DOM0 methods
							currentNode.setAttribute('for', AccountSection.incrementNode(currentNode.getAttribute('for'), listItemIndex));
						}
					}

					if ( (currentNode.id) && (currentNode.id.length > 0) )
					{
						currentNode.id = AccountSection.incrementNode(currentNode.id, listItemIndex);
					}


					if ( (currentNode.name) && (currentNode.name.length > 0) )
					{
						currentNode.name = AccountSection.incrementNode(currentNode.name, listItemIndex);
					}


					if (currentNode.tagName.toLowerCase() == 'input')
					{
						var typeAttribute = currentNode.getAttribute('type');

						if ((currentNode.type) &&
							((currentNode.type.toLowerCase() == 'image') || (currentNode.type.toLowerCase() == 'submit')) )
						{
							currentNode.value = AccountSection.incrementNode(currentNode.value, listItemIndex);
						}
					} // end of if-statement for input-fields

				} // end of if-statement for element-nodes

			} // end of inner for-loop (re: attributes / child nodes of the list item)

		} // end of for-loop (re: each list item)

	},

	/* This takes care of all the event-handler bits for the
		Authorized Contact fields, including rejiggering Enter so
		that it triggers "Add Another" rather than delete, and
		hooking up the action for the "Delete" buttons */
	applyEventHandlersToAuthorizedContactsFields : function(listItem)
	{
		listItem = $(listItem);

		listItem = listItem.walkUpUntil('li'); // make sure we're dealing with the list item

		// If no list item was found, exit
		if (listItem == null) return;


		// Hook up the onkeypress handler (for redirecting Enter)

		/* This rejiggers any Enter keypresses so that pressing Enter adds a field
		rather than deleting it (which is what would naturally occur since the delete
		submit-buttons are adjacent to the text fields) */

		var textField = $A( document.getElementsByClassName('textfield', listItem) ).first();

		textField.onkeypress = function(event)
		{
			if (!event) var event = window.event;

			if (event.keyCode)
				var characterCode = event.keyCode;
			else if (event.which)
				var characterCode = event.which;

			// 13 = "Enter"
			if (characterCode == 13)
			{
				AccountSection.addAnotherField(this.parentNode);

				// disable the keypress from its normal course of action
				Event.stop(event);
				return false;
			}
		}

		textField = null; // avoid memory leaks


		// Hook up the Delete button

		/* This is used for the Authorized Contacts section of the Account Information
		page -- when a "Delete this" button is clicked, this removes that item */

		var deleteButton = $A( document.getElementsByClassName(AccountSection.INPUT_ELEMENT_DELETE_THIS_CONTACT_CLASSNAME, listItem) ).first();

		// Traditional event handlers are used here for consistency
		// with cloned elements (IE clones attachEvent event-handlers, while
		// the W3C event mode, addEventListener, does not clone event-handlers)
//		deleteButton.onclick = function()
//		{
//			AccountSection.deleteThisField(this.parentNode);
//			return false;
//		}

		// avoid memory leaks
		deleteButton = null;
		listItem = null;
	},

	deleteThisField : function(element)
	{
		element = $(element);

		/* Walk up the tree until the parent li element is found */

		var parentListItem = element.walkUpUntil('li');

		// If no parent list item was found, exit
		if (parentListItem === null) return;

		// Then walk up further to the UL and count how many children li elements there
		// are -- if there's only one, then don't allow it to be deleted :)

		var parentUnorderedList = parentListItem.walkUpUntil('ul');

		// If no parent unordered list was found, exit
		if (parentListItem === null) return;

		// Then, check to make sure there's more than one list item before deleting anything
		var numberOfChildListItems = $A( parentUnorderedList.getElementsByTagName('li') ).length;

		if (numberOfChildListItems > 1)
		{
			Element.remove(parentListItem);

			// Prevent memory leaks
			parentListItem = null;
			element = null;

			// Since an element was just deleted, renumber the list
			// (to prevent holes in the sequence)
			AccountSection.renumberListItems(parentUnorderedList);
		}
	},

	addAnotherField : function (element)
	{

		/* Walk up the tree until one of the parent element has an unordered list child */

		var parentElement = element; // initialize;

		var unorderedListChildren = $A( parentElement.getElementsByTagName('ul') ); // initialize

		// Walk up the tree until you run into a parent element with ul children (or the body element)
		while ((unorderedListChildren.length == 0) && (parentElement.tagName.toLowerCase() != "body"))
		{
			parentElement = parentElement.parentNode;
			unorderedListChildren = $A(parentElement.getElementsByTagName('ul'));
		}

		// If it walked all the way up the tree and found no appropriate parent elements, exit
		if (parentElement.tagName.toLowerCase() == "body") return;

		// Grab the first unordered list from the set
		var unorderedList = unorderedListChildren.first();

		var listItemChildren = $A( unorderedList.getElementsByTagName('li') );

		var newListItem = listItemChildren.last().cloneNode(true);

		unorderedList.appendChild(newListItem);

		// Loop through the child nodes of the newListItem and
		// empty the text field's value
		for (var i = 0, len = newListItem.childNodes.length; i < len; i++)
		{
			var currentNode = newListItem.childNodes[i];

			// "1" = element node
			if (currentNode.nodeType == 1)
			{
				if ((currentNode.type) &&
					(currentNode.type.toLowerCase() == 'text') &&
					(currentNode.value) &&
					(currentNode.value.length > 0) )
				{
					currentNode.value = "";
				}
			}

		} // end of for-loop


		// Since an element was just added, renumber the list
		// (to avoid id/name collisions)
		AccountSection.renumberListItems(unorderedList);

		// Reapply the eventSelectors rules so that they
		// apply to the newly-created element(s) too
		AccountSection.applyEventHandlersToAuthorizedContactsFields(newListItem);

		var textFields = $$('dd#' +AccountSection.ADD_CONTACTS_AUTHORIZED_CONTACTS_ID +'ul li input[type=text]');

		// Then focus the newly-added field
		// (the setTimeout is needed, seemingly,
		// to allow the rest of the script to "catch up"
		// in terms of rejiggering the fields, before
		// the field is focused
		window.setTimeout(function()
			{
				Field.focus( textFields.last() );
			}.bind(this), 10);

		newListItem = null; // prevent memory leaks

		return false; // prevent the submit button from firing

	},

	/* This applies event handlers to the form elements
		within the Outstanding Tolls box -- it's not a
		traditional event:Selectors scenario since these guys
		generally need to be (re)applied manually once
		the markup is Ajaxically yoinked from the back-end */

	/*
		This function is currently deprecated, though it could
		be reinstated if the form is later Ajaxified.
		-- 2006-10-06 / AB
	*/
	/* applyEventHandlersToOutstandingTollsFormElements : function(element)
	{
		element = $(element);

		// Walk up the tree to the parent unordered list,
		// in case one of the list's children was sent in
		if (element.tagName.toLowerCase() != 'ul') element = element.walkUpUntil('ul');

		// If no parent unordered list was found, exit
		if (element === null) return;

		var removeThisVehicleButtons = $A( document.getElementsByClassName('remove-this-vehicle', element) );

		removeThisVehicleButtons.each( function(removeThisVehicleButton)
		{
			// Old-style traditional event handlers are used here, in case this
			// function needs to be reapplied (since advanced event handlers
			// would just accumulate on the object)

			removeThisVehicleButton.onclick = function()
			{
				hideIbox();
				AccountSection.showSelectElements();
				return false;
			};

			// avoid memory leaks
			removeThisVehicleButton = null;

		}.bind(this));

		element = null;
	}, */


	/* This checks if the browser is IE and, if so,
		hides all the select elements -- this is necessary
		since select elements bleed through absolutely-positioned
		content in the browser */
	hideSelectElements : function()
	{
		// If it's not IE, nevermind
		if (!document.all) return;

		var selectElements = $A( document.getElementsByTagName('select') );

		selectElements.each(function(selectElement) {
			Element.hide(selectElement)
		}.bind(this));
	},

	/* This checks if the browser is IE and, if so,
		unhides (shows) all the select elements -- this is
		the reverse side of the hiding-bits of which that's necessary
		since select elements bleed through absolutely-positioned
		content in the browser */
	showSelectElements : function()
	{
		// If it's not IE, nevermind
		if (!document.all) return;

		var selectElements = $A( document.getElementsByTagName('select') );

		selectElements.each(function(selectElement) {
			Element.show(selectElement);
		}.bind(this));
	},


	/* This is mostly used on the Billing Information page and this is triggered by
		the checkbox for "My billing address is different than my mailing address" */
	showOrHideBillingAddressFields : function (isDifferentFromMailingAddress)
	{
		switch (isDifferentFromMailingAddress)
		{
			case true:
				Element.show(AccountSection.FIELDSET_ELEMENT_BILLING_ADDRESS_FIELDS);
				break;

			case false:
				Element.hide(AccountSection.FIELDSET_ELEMENT_BILLING_ADDRESS_FIELDS);
				break;
		}
	},

	/* This is mostly used on the Billing Information page and this is triggered by
		the checkbox for "I would like to provide an alternate credit card" */
	showOrHideAlternateCreditCardFields : function (shouldShowAlternateCreditCardFields)
	{
		switch (shouldShowAlternateCreditCardFields)
		{
			case true:
				/* Display the fields for an alternate credit card */
				Element.show(AccountSection.ALTERNATE_CREDIT_CARD_FIELDS_ID);
				break;

			case false:
				/* Hide the fields for an alternate credit card */
                                document.getElementById("alternate-card-expiration-month").value="";
                                document.getElementById("alternate-card-expiration-year").value="";
                                document.getElementById("name-on-alternate-credit-card").value="";
                                document.getElementById("alternate-card-number").value="";
                                document.getElementById("alternate-card-type").value="";
				Element.hide(AccountSection.ALTERNATE_CREDIT_CARD_FIELDS_ID);
				break;
		}

	},

	/*******	this requirement was deleted. I've kept the code here in case it comes back - 11/02/06 *****/
	/* This is mostly used on the Billing Information page and this is triggered by
		the checkbox for "I would like to provide an alternate EFT account" */
	/* showOrHideAlternateEFTAccountFields : function (shouldShowAlternateEFTAccountFields)
	{
		switch (shouldShowAlternateEFTAccountFields)
		{
			case true:
				/* Display the fields for an alternate EFT account */
	/*			Element.show(AccountSection.ALTERNATE_EFT_ACCOUNT_FIELDS);
				break;

			case false:
				/* Hide the fields for an alternate EFT account */
	/*			Element.hide(AccountSection.ALTERNATE_EFT_ACCOUNT_FIELDS);
				break;
		}

	},*/

	/* If it's not already visible, this shows the
		"Make this my primary credit card for EZ Account rebilling."
		field and highlights it. It's designed to be used in conjunction
		with Common.runFunctionIfDestructiveKeydown for the keypress
		event handler of the input fields above this guy. */
	showMakeThisMyPrimaryCardParagraphIfNeeded : function()
	{
		var makeThisMyPrimaryCardParagraph = $(AccountSection.PRIMARY_CARD_FOR_REBILLING_PARAGRAPH_ID);
		// If it's not currently visible, show it and then highlight it
		if (makeThisMyPrimaryCardParagraph != null && !makeThisMyPrimaryCardParagraph.visible() )
		{
		 	makeThisMyPrimaryCardParagraph.show();
		 	new Effect.Highlight(makeThisMyPrimaryCardParagraph);
	 	}

	},

	/* If it's not already visible, this shows the
		"Update my billing address using this information."
		field and highlights it. It's designed to be used in conjunction
		with Common.runFunctionIfDestructiveKeydown for the keypress
		event handler of the input fields above this guy. */
	showUpdateMyBillingAddressParagraphIfNeeded : function()
	{
		var updateMyBillingAddressParagraph = $(AccountSection.UPDATE_BILLING_ADDRESS_FIELD_PARAGRAPH_ID);
		// If it's not currently visible, show it and then highlight it
		if ( updateMyBillingAddressParagraph != null && !updateMyBillingAddressParagraph.visible() )
		{
		 	updateMyBillingAddressParagraph.show();
		 	new Effect.Highlight(updateMyBillingAddressParagraph);
	 	}
	},

	/* This accepts a checkbox and then checks (or unchecks)
		all elements which are in the same position within the table.

		For instance, if a particular select-all checkbox is the 2nd input
		element in a row, then this goes through the table and checks
		the other checkboxes which are also the 2nd input elements
		in their respective rows
	*/
	selectAll : function(checkboxElement)
	{
		var isChecked = checkboxElement.checked;

		/* First, find out what position this element is within its row */

		var parentRow = checkboxElement.parentNode; // initialize;

		// Walk up the tree until you run into a table row element (or the body element)
		while ((parentRow.tagName.toLowerCase() != "tr") && (parentRow.tagName.toLowerCase() != "body"))
		{
			parentRow = parentRow.parentNode;
		}

		// If it walked all the way up the tree and found no appropriate parent elements, exit
		if (parentRow.tagName.toLowerCase() == "body") return;

		var checkboxSiblings = $A( parentRow.getElementsByTagName('input') );

		// Loop through the checkboxSiblings until a match is found

		var thisCheckboxElementPosition = 0; // initialize (assuming '0' represents the first item)

		while (checkboxSiblings[thisCheckboxElementPosition] != checkboxElement)
		{
			thisCheckboxElementPosition++;
		}

		/* Then go through the other rows and activate/deactivate
			the elements which are in that same row position */

		var parentTable = parentRow.parentNode; // initialize

		// Walk up the tree until you run into a table / thead / tbody element (or the body element)
		while ( (parentTable.tagName.toLowerCase() != "table") &&
				(parentTable.tagName.toLowerCase() != "body") )
		{
			parentTable = parentTable.parentNode;
		}

		// If it walked all the way up the tree and hit the body element
		// instead of a table, something Really Bad(tm) occurred, so exit
		if (parentTable.tagName.toLowerCase() == "body") return;

		var allTableRows = $A( parentTable.getElementsByTagName('tr') );

		// Then loop through all those table rows and activate/deactivate the checkboxes
		//	in that row as needed

		allTableRows.each( function(tableRow) {

			var thisRowsInputElements = $A( tableRow.getElementsByTagName('input') );

			/* Make sure that:
				* This row has at least as many checkboxes as the position of the sent-in checkbox
				* And, if that's true, that the checkbox in the position of the send-in checkbox
					isn't the /sent-in checkbox/
			*/

			// Make sure that this isn't the row which contains the select-all checkbox
			// that was just sent in and, if not, set that checkbox to match the guy that was sent in
			if ((thisRowsInputElements.length >= (thisCheckboxElementPosition + 1) ) &&
				(thisRowsInputElements[thisCheckboxElementPosition] != checkboxElement))
			{
				thisRowsInputElements[thisCheckboxElementPosition].checked = isChecked;
			}

		}.bind(this)); // end of each
	},

	/* This accepts a row as an argument and returns an array of all
		td/th cells	which have the class CURRENCY_CLASSNAME */
	getCurrencyCells : function(trElement)
	{
		trElement = $(trElement);

		/* Since the function is flexible enough to accept
			any children of the table row, walk up the tree until
			hitting a tr (if the current element isn't one already) */

		trElement = trElement.walkUpUntil('tr');

		// If no parent tr element was found, exit
		if (trElement === null) return;

		var currencyCells = []; // initialize

		// Go through each of the table row's children nodes,
		// check if it's an element node and, if so, check if
		// it has the class CURRENCY_CLASSNAME

		// Loop through the child nodes of the trElement find the first
		// table cell with the class CURRENCY_CLASSNAME and return its value
		for (var i = 0, trLength = trElement.childNodes.length; i < trLength; i++)
		{
			var trChild = trElement.childNodes[i];

			// "1" = element node
			if ((trChild.nodeType == 1) &&
				((trChild.tagName.toLowerCase() == 'td') || (trChild.tagName.toLowerCase() == 'th')) &&
				Element.hasClassName(trChild, AccountSection.CURRENCY_CLASSNAME))
			{
				currencyCells.push(trChild);

			} // end of if-statement for finding CURRENCY_CLASSNAME cells

		} // end of for-loop for looping through trElement children


		return (currencyCells);

	},

	/* This accepts a th or td element object and returns
		the currency value found inside it */
	getCurrency : function(tableCellElement)
	{
		// If the element is undefined or null, return 0
		if (tableCellElement === null || tableCellElement === undefined) return 0.0;

		tableCellElement = $(tableCellElement);

		for (var i = 0, tableCellChildrenLength = tableCellElement.childNodes.length; i < tableCellChildrenLength; i++)
		{
			var tableCellChild = tableCellElement.childNodes[i];

			// "3" = text node (this way, span elements are skipped over)
			if ((tableCellChild.nodeType == 3) && (tableCellChild.length > 0))
			{
                          // MB 2010-03-12: strip out the ',' thousands separator.
				return (tableCellChild.nodeValue.trim().replace(/,/g,''));
			}

		} // end of for-loop for looping through table cell children

	},

	/* This accepts a th or td element object along with
		a currency value and applies the new currency value
		to the cell */
	setCurrency : function(tableCellElement, currencyValue)
	{
		/*
			First, delete any existing currency bits
			(this wouldn't normally be necessary, but
			Safari 2.x is apparently unable to modify
			whitespace text nodes)
		*/
		this.deleteTextNodes(tableCellElement);

		tableCellElement = $(tableCellElement);
		currencyValue = currencyValue.trim(); // remove any preceding or trailing whitespace

		for (var i = 0, trChildrenLength = tableCellElement.childNodes.length; i < trChildrenLength; i++)
		{
			var tableCellChild = tableCellElement.childNodes[i];

			// "3" = text node (this way, label, input, and span elements are skipped over)
			if ((tableCellChild.nodeType == 3) && (tableCellChild.nodeValue.length > 0))
			{
				tableCellChild.nodeValue = currencyValue;
				return;
			}

		} // end of for-loop for looping through table cell children

		// If it got this far without exiting yet, it must not have found an applicable
		// text node. So, create one and append it to the cell

		var currencyValueNode = document.createTextNode(currencyValue);
		tableCellElement.appendChild(currencyValueNode);

	},

	/* This accepts an element and removes all the text nodes (such as text
		nodes representing currency) */
	deleteTextNodes : function(element)
	{
		element = $(element);

		var elementChild = element.firstChild; // initialize

		while (elementChild != null)
		{
			// The next sibling is calculated before any possible
			// deletions since, after a deletion, there would be
			// no element from which to derive a sibling
			var nextNode = elementChild.nextSibling;

			// "3" = text node (this way, span elements are skipped over)
			if (elementChild.nodeType == 3)
			{
				element.removeChild(elementChild);
			}

			elementChild = nextNode;

		} // end of for-loop for looping through table cell children

		element = null; // avoid memory leaks

	},

	/* This takes a payment-table as a parameter and adds up
		the right-most currency cells in each row, placing
		the total into the last currency cell in the tfoot */
	updateTotal : function(startingElement)
	{
		startingElement = $(startingElement);

		var tableElement = startingElement.walkUpUntil('table');

		// If no parent table was found, check if this guy was actually
		// a text field inside a fieldset.add-to-ez-account-fields area
		if (tableElement === null)
		{
			var fieldsetElement = startingElement.walkUpUntil('fieldset');

			// If no fieldset was found, or a fieldset was found but it
			// doesn't have the ADD_TO_EZ_ACCOUNT_CLASSNAME, then
			// there's not much more we can do
			if ((fieldsetElement == null)  || (!Element.hasClassName(fieldsetElement, AccountSection.FIELDSET_ELEMENT_ADD_TO_EZ_ACCOUNT_CLASSNAME)))
			{
				return;
			}

			// If it got this far, then we must be dealing with a fieldset.ADD_TO_EZ_ACCOUNT_CLASSNAME

			/* So, find the first table with PAYMENTS_TABLE_CLASSNAME and go with that */

			// ===

			var documentTables = $A(document.getElementsByTagName('table') );

			var arrayIndex = 0; // initialize

			var documentTablesLength = documentTables.length;
			var hasFoundTable = false; // initialize

			while ((!hasFoundTable) && (arrayIndex < documentTablesLength))
			{
				currentTable = documentTables[arrayIndex];

				if (Element.hasClassName(currentTable, AccountSection.PAYMENTS_TABLE_CLASSNAME))
				{
					hasFoundTable = true;
					tableElement = currentTable;
				}

				arrayIndex++;
			}

			// ===

			// If that came up empty, well, that's it
			if (!tableElement) return;

		} // end of if for testing whether the starting element was inside a table


		// Grab the tbody

		var tbodyElements = $A( tableElement.getElementsByTagName('tbody') );

		var total = 0; // initialize
                var skipUpdate = false; // used if a onclick is explicitly initiated in the tablerow function

		tbodyElements.each(function(tableBody) {

			// Grab all the rows
			var trElements = $A( tableBody.getElementsByTagName('tr') );

                        // MB 2010.01.06 - Get the online discount line items and hide them.
                        var collapsed = tableBody.hasClassName(AccountSection.COLLAPSED_CLASSNAME);
                        var onlineDiscounts = tableBody.getElementsByClassName(AccountSection.ONLINE_DISCOUNT_CLASSNAME);
                        var onlineDiscountClass = AccountSection.COLLAPSED_CLASSNAME;
                        var waivers = tableBody.getElementsByClassName(AccountSection.WAIVER_CLASSNAME);
                        var waiverClass = AccountSection.COLLAPSED_CLASSNAME;
                        var adjustments = tableBody.getElementsByClassName(AccountSection.ADJUSTMENT_CLASSNAME);
                        var adjustmentClass = AccountSection.COLLAPSED_CLASSNAME;
                        var invoiceTotal = 0; // initialize

			trElements.each(function(tableRow) {

				tableRow = $(tableRow);

				// Only pay attention to the "invoice-summary" table rows
				if (tableRow.hasClassName(AccountSection.INVOICE_SUMMARY_CLASSNAME)) {
                                  var currencyCells = AccountSection.getCurrencyCells(tableRow);

                                  // Get the select element for each of those and, depending
                                  // on how it's set, add up the values for each row
                                  var selectElement = $A( tableRow.getElementsByTagName('select') ).first();

                                  switch ( $F(selectElement) ) {
                                  case AccountSection.SELECT_ELEMENT_PAY_IN_FULL_VALUE:
                                    invoiceTotal = parseFloat( AccountSection.getCurrency(currencyCells.first()) );
                                    if (invoiceTotal > 0)
                                      total += invoiceTotal;
                                    //total = total.toFixed(2);
                                    // TODO: online discount disabled for TO488-option1
                                    //onlineDiscountClass = AccountSection.EXPANDED_CLASSNAME;
                                    break;

                                  case AccountSection.SELECT_ELEMENT_WAIVE_VALUE:
                                    // TODO: the actual discount amount should be applied -- not the hard-coded value of 30.0
                                    invoiceTotal = parseFloat( AccountSection.getCurrency(currencyCells.first()) ) - 30.0;
                                    total += invoiceTotal;
                                    waiverClass = AccountSection.EXPANDED_CLASSNAME;
                                    var cb = document.getElementById(AccountSection.PAY_ALL_WAIVE_ID);
                                    // if the user selected the dropdown option, then check the box and delegate processing
                                    if (!cb.checked && !selectElement.disabled) {
                                      cb.checked = true;
                                      cb.onclick();
                                      skipUpdate = true;
                                    }
                                    break;

                                  case AccountSection.SELECT_ELEMENT_ADJUST_VALUE:
                                    // TODO: the actual discount amount should be applied -- not the hard-coded value of 15.0
                                    invoiceTotal = parseFloat( AccountSection.getCurrency(currencyCells.first()) ) - 15.0;
                                    total += invoiceTotal;
                                    adjustmentClass = AccountSection.EXPANDED_CLASSNAME;
                                    var cb = document.getElementById(AccountSection.PAY_ALL_ADJUST_ID);
                                    // if the user selected the dropdown option, then check the box and delegate processing
                                    if (!cb.checked && !selectElement.disabled) {
                                      cb.checked = true;
                                      cb.onclick();
                                      skipUpdate = true;
                                    }
                                    break;

                                  case AccountSection.SELECT_ELEMENT_SPECIFY_AMOUNT_VALUE:
                                    var inputElement = $A( currencyCells.last().getElementsByTagName('input') ).first();
                                    var amountToAdd = parseFloat( $F(inputElement) );
                                    amountToAdd = parseFloat(amountToAdd.toFixed(2));

                                    // Make sure it's a number before trying to add it
                                    // (in case the input field is empty, for instance)
                                    if (!isNaN(amountToAdd)) {
                                      total += amountToAdd;
                                      total = parseFloat(total.toFixed(2));
                                    }
                                    // DON'T BREAK! -- invoiceTotal needs to be set

                                  default:
                                    // case AccountSection.SELECT_ELEMENT_DONT_PAY_VALUE:
                                    // If "don't pay" is selected, there's no need to update the total
                                    invoiceTotal = parseFloat( AccountSection.getCurrency(currencyCells.first()) );
                                    break;

                                  } // end of switch-statement

				} // end of if-statement for "invoice-summary" rows

			}.bind(this)); // end of tableRow loop

                        if (skipUpdate) return;

                        // MB 2010.01.06 - update the online discount line items display.
                        var onlineDiscountDisplay = (!collapsed && onlineDiscountClass == AccountSection.EXPANDED_CLASSNAME ? '' : 'none');
                        for (var i = 0; i < onlineDiscounts.length; i++) {
                          onlineDiscounts[i].style.display = onlineDiscountDisplay;
                          AccountSection.setExpandOrCollapseClass(onlineDiscounts[i], onlineDiscountClass);
                        }
                        var waiverDisplay = (!collapsed && waiverClass == AccountSection.EXPANDED_CLASSNAME ? '' : 'none');
                        for (var i = 0; i < waivers.length; i++) {
                          waivers[i].style.display = waiverDisplay;
                          AccountSection.setExpandOrCollapseClass(waivers[i], waiverClass);
                        }
                        var adjustmentDisplay = (!collapsed && adjustmentClass == AccountSection.EXPANDED_CLASSNAME ? '' : 'none');
                        for (var i = 0; i < adjustments.length; i++) {
                          adjustments[i].style.display = adjustmentDisplay;
                          AccountSection.setExpandOrCollapseClass(adjustments[i], adjustmentClass);
                        }
                        // Grab the invoice total and update the value
                        var totals = tableBody.getElementsByClassName(AccountSection.TOTALS_CLASSNAME);
                        var totalDueRow = totals[totals.length-1];
                        AccountSection.setCurrency(AccountSection.getCurrencyCells(totalDueRow).first(),
                                                   invoiceTotal.toString().toCurrency());

		}.bind(this)); // end of tableBody loop

                if (skipUpdate) return;

		// Grab the tfoot
		var tfootElement = $A( tableElement.getElementsByTagName('tfoot') ).first();
		var tfootRow = $A( tfootElement.getElementsByTagName('tr') ).first();

		var tfootCurrencyCells = AccountSection.getCurrencyCells(tfootRow);

		// Put the total value in the last tfoot currency-cell

		AccountSection.setCurrency(tfootCurrencyCells.last(), total.toString().toCurrency() );

		// Then, further update the total to add in the bits for the ADD_TO_EZ_ACCOUNT_CLASSNAME text field
		// (if there is one)

		/*
			(tableElement.parentNode.parentNode is chosen so that enough of the DOM is search to find the
			target elements, but not quite all of it, so as to save time searching. As you might guess,
			tableElement.parentNode doens't quite work since the tableElement and the targetElements
			aren't direct siblings.)
		*/
		var documentFieldsets = $A(tableElement.parentNode.parentNode.getElementsByTagName('fieldset') );

		var addToEZAccountFieldsets = []; // initialize

		for (var i=0, len = documentFieldsets.length; i < len; i++)
		{
			var currentFieldset = documentFieldsets[i];

			if (Element.hasClassName(currentFieldset, AccountSection.FIELDSET_ELEMENT_ADD_TO_EZ_ACCOUNT_CLASSNAME))
			{
				addToEZAccountFieldsets.push(currentFieldset);
			}

		}

		var grandTotal = total; // initialize

		// Loop through all the add-to-ez-account fieldsets and process them
		for (var i=0, addToEZAccountFieldsetsLength = addToEZAccountFieldsets.length; i < addToEZAccountFieldsetsLength; i++)
		{
			// Then, grab all its children input fields
			var addToEZAccountTextFields = addToEZAccountFieldsets[i].getElementsByTagName('input');

			// Then, loop through all its input fields
			for (j=0, addToEZAccountTextFieldsLength = addToEZAccountTextFields.length; j < addToEZAccountTextFieldsLength; j++)
			{
				grandTotal = grandTotal + parseFloat( $F(addToEZAccountTextFields[j]) );

			} // end of input-field for-loop

		} // end of add-to-ez-account fieldsets loop


		// Then, process the total-payment paragraphs

		/*
			(tableElement.parentNode.parentNode is chosen so that enough of the DOM is search to find the
			target elements, but not quite all of it, so as to save time searching. As you might guess,
			tableElement.parentNode doens't quite work since the tableElement and the targetElements
			aren't direct siblings.)
		*/
		var documentParagraphs = $A(tableElement.parentNode.parentNode.getElementsByTagName('p') );

		var totalPaymentParagraphs = []; // initialize

		for (var i=0, len = documentParagraphs.length; i < len; i++)
		{
			var currentParagraph = documentParagraphs[i];

			if (Element.hasClassName(currentParagraph, AccountSection.PARAGRAPH_TOTAL_PAYMENT_CLASSNAME))
			{
				totalPaymentParagraphs.push(currentParagraph);
			}

		}

		var grandTotalCurrencyString = grandTotal.toString().toCurrency();

		for (i=0, len = totalPaymentParagraphs.length; i < len; i++)
		{
			var totalPaymentParagraph = totalPaymentParagraphs[i];

			// Then, grab its first child element of the payment-paragraph as the target element for the grand total
			var totalPaymentTargetElement = $A( totalPaymentParagraph.getElementsByTagName('*') ).first();

			AccountSection.setCurrency(totalPaymentTargetElement, grandTotalCurrencyString );

		} // end of for-loop for the payment paragraphs

	},

	/*
		Given a starting element, this walks up the
		tree until it finds an appropriate block-level
		element, then steps through the DOM until
		a table sibling is found.

		In terms of "appropriate block-level elements",
		this would, for example, derive the "dl"
		if fed in a "dd" or derive a "ul" if fed in an
		"li".
	*/
	findNearestTable : function(startingElement)
	{
		startingElement = $(startingElement);

		var parentElement = startingElement.walkUpUntil(['ul', 'ol', 'dl', 'p', 'table']);

		// If no meaningful parent element was found, exit
		if (parentElement === null) return;

		/* Then, check each of the element's siblings until a table is found */

		// First check if the parentElement is a table
		if (parentElement.tagName.toLowerCase() == "table")
		{
			return parentElement;
		}

		while (parentElement.nextSibling)
		{
			parentElement = parentElement.nextSibling;

			// "1" = element node
			if ((parentElement.nodeType == 1) && (parentElement.tagName.toLowerCase() == "table"))
			{
				return parentElement;
			}

		} // end of while loop

		// If it got this far, no sibling table element was found,
		// so return null

		return null;
	},

	/* This runs expandOrCollapseTBody() on all the tbody children
		within a table */
	expandOrCollapseAllTBodyChildren : function(tableElement, newState)
	{
		tableElement = $(tableElement);

		// Make sure we're dealing with a table element
		tableElement = tableElement.walkUpUntil('table');

		// If no table elements were found, exit
		if (tableElement === null) return;

		var tbodyElements = $A( tableElement.getElementsByTagName('tbody') );

		tbodyElements.each(function (tbodyElement) {
			AccountSection.expandOrCollapseTBody(tbodyElement, newState);
		}.bind(this));

	},

	/* This accepts a tbody elment and a desired newState, of
		which the latter is either "expand" or "collapse" */
	expandOrCollapseTBody : function(tbodyElement, newState)
	{
		tbodyElement = $(tbodyElement);

		// Walk up the tree to find the parent tbody
		// (in case that wasn't what was sent in)

		var tbodyElement = tbodyElement.walkUpUntil('tbody');

		// If no parent tbody element was found, exit
		if (tbodyElement === null) return;

		// Then flip the class on that guy (if no change made, exit)
		if (!AccountSection.setExpandOrCollapseClass(tbodyElement, newState)) return;

		// Grab all the child tr elements in preparation for hiding/showing them
		var childrenTableRows = $A( tbodyElement.getElementsByTagName('tr') );

		// Then, hide or show the children tr elements as needed

		childrenTableRows.each( function(tableRow) {

			tableRow = $(tableRow);

			// Make sure that we're only working on direct-child
			// table rows (so as to avoid messing with nested table rows)
			//
			// Also, skip over any table rows that have the classname INVOICE_SUMMARY_CLASSNAME
			if ((tableRow.parentNode == tbodyElement) && (!tableRow.hasClassName(AccountSection.INVOICE_SUMMARY_CLASSNAME)))
			{
				switch (newState)
				{
					case AccountSection.EXPANDED_CLASSNAME:
						if (!tableRow.hasClassName(AccountSection.COLLAPSED_CLASSNAME))
							Element.show(tableRow);
						break;

					case AccountSection.COLLAPSED_CLASSNAME:
						Element.hide(tableRow)
						break;

					default:
						alert("Error - neither 'expand' nor 'collapse' sent in for childrenTableRows.each");
						break;
				}
			}

		}.bind(this));

		// Just in case the calling function needs it, return the tbody
		// element that was sent in.
		return tbodyElement;
	},

        /* This accepts an element and a desired class newState, of
           which the latter is either "expand" or "collapse".
           Returns true is element class modified.
           2010.01.06 Milosh Boroyevich */
        setExpandOrCollapseClass : function(element, newState)
        {
          element = $(element);

          // If element is null, exit
          if (element === null) return false;

          switch (newState) {
          case AccountSection.EXPANDED_CLASSNAME:
            if (!element.hasClassName(AccountSection.EXPANDED_CLASSNAME)) {
              element.removeClassName(AccountSection.COLLAPSED_CLASSNAME);
              element.addClassName(AccountSection.EXPANDED_CLASSNAME);
              return true;
            }
            break;

          case AccountSection.COLLAPSED_CLASSNAME:
            if (!element.hasClassName(AccountSection.COLLAPSED_CLASSNAME)) {
              element.removeClassName(AccountSection.EXPANDED_CLASSNAME);
              element.addClassName(AccountSection.COLLAPSED_CLASSNAME);
              return true;
            }
            break;
          }

          return false;
        },

	/* This is mostly used for Payments tables. Triggered by an
		onclick event handler on a table row, it then walks
		up the tree until it finds a tbody element; then
		it swaps its class:
			"expanded" -> "collapsed"
			"collapsed" -> "expanded"

		Naturally, this has safeguards to prevent the table row's
		onclick being triggered from other meaningful clicks,
		such as on labels or input fields.
	*/
	processTBodyClickEventHandler : function(element, event)
	{
		element = $(element);

		var eventTargetElement = Common.getTargetElement(event);
		var eventTargetTagName = eventTargetElement.tagName.toLowerCase();

		// If the click event was on a label, or a form field, exit
		// anchors are specifically skipped since they require their own check
		if ((eventTargetTagName == 'label') ||
			(eventTargetTagName == 'input') ||
			(eventTargetTagName == 'select') ||
			(eventTargetTagName == 'option') ||
			(eventTargetTagName == 'optgroup'))
		{
			return;
		}

		// For anchors, they need to pass through the event if they're
		// on an invoice-summary line. But for anchors elsewhere, they
		// shouldn't allow the event to continue
		if (eventTargetTagName == 'a')
		{
			var parentRow = $(eventTargetElement).walkUpUntil('tr');

			// Except for parent rows that have the class "invoice-summary", exit
			if (!parentRow.hasClassName(AccountSection.INVOICE_SUMMARY_CLASSNAME))
			{
				return;
			}
		}

		// With that out of the way, let's walk up the tree to find the parent tbody
		// and flip the classes as needed

		var tbodyElement = element.walkUpUntil('tbody');

		// If no parent tbody element was found, exit
		if (tbodyElement === null) return;

		// Then flip the class on that guy

		if (tbodyElement.hasClassName(AccountSection.EXPANDED_CLASSNAME))
		{
			AccountSection.expandOrCollapseTBody(tbodyElement, AccountSection.COLLAPSED_CLASSNAME);
		}
		else if (tbodyElement.hasClassName(AccountSection.COLLAPSED_CLASSNAME))
		{
			AccountSection.expandOrCollapseTBody(tbodyElement, AccountSection.EXPANDED_CLASSNAME);
	 	}

	},

	/* This function is applies to the onkeypress event handler for payment
		text fields. It rejects letters and other non-numeric characters.

		Of note, it also disallows minus signs ("-"). In this case, that's
		intentional, but that behavior may not necessarily be desired if
		this function were to be repurposed for other text fields.
	*/
	processPaymentsTableTextfieldKeys : function(element, event)
	{
		element = $(element);

		if (!event) var event = window.event;

		/*
			For onkeypress, Firefox uses charCode while ignoring
			keyCode, while IE uses keyCode while ignoring charCode.
			See also this page: http://www.texotela.co.uk/keypress.php

			Also, these are being compared against "undefined" (rather than
			just checking "if (event.charCode)" to allow for zero-values
			(which are fine but which would fail the simpler if-statement).
		*/

		if (event.charCode != undefined) // Firefox/Safari path
		{
			var key = event.charCode;

			/*
				Firefox returns an event.which of 0 for directional
				keys, while Safari returns 62###

				(event.charCode isn't reused for this test since
				backspace triggers an event.charCode of 8 while
				its event.which remains at 0.)
			*/
			if ((event.which == 0) || (event.which > 1000)) return;
		}
		else if (event.keyCode != undefined) // IE path
		{
			var key = event.keyCode;
		}

		/* This next bit of code disallows dollar signs,
			negative signs, and letters. It also
			only allows one decimal point at most */

		// 8 -> [backspace]
		// 36 -> "$"
		// 37 -> [left arrow]
		// 38 -> [up arrow]
		// 39 -> [right arrow]
		// 40 -> [down arrow]
		// 45 -> "-"
		// 46 -> "."
		// 48 -> "0"
		// 57 -> "9"
		// [Also, Safari assigns arrow keys and such to the 65,000 range, hence
		// the check against 1000 below.]
		if ( ((key >= 41) && (key <= 45)) ||
			((key >= 58) && (key <= 1000)) ||
			((key <= 36) && (key >= 32)) )
		{
			Event.stop(event);
			return false;
		}
		else
		{
			// Test for multiple decimal points
			if (key == 46)
			{
				var decimalPointArray = $F(element).split('.');

				/*
					If there're no decimal points, like "40", the array will have one element, ["40"]
					If there's one decimal points, like "4.0", the array will have two element, ["4", "0"]
					If there're two decimal points, like "4.0.", the array will have three or more elements, ["4", "0", ""]
				*/

				if (decimalPointArray.length >= 2)
				{
					Event.stop(event);
					return false;
				}
			}

			// If it got this far, then it must be fine
			AccountSection.updateTotal(element);
			return true;
		}

	},

	/*
	 	When the page is loaded -- and also when any of the select elements in a payment
	 	table are changed -- propagate the monetary amounts to the
	 	[Selected] Payment(s) column
	 */
	populatePaymentCell : function(selectElement)
	{
		selectElement = $(selectElement);

		// Walk up the tree until the parent tr element is found

		var trElement = selectElement.walkUpUntil('tr');

		// If no parent tr element was found, exit
		if (trElement === null) return;

		var thisRowCurrencyCells = AccountSection.getCurrencyCells(trElement);

		var amountDue = AccountSection.getCurrency(thisRowCurrencyCells.first() );
		var inputElement = $A( thisRowCurrencyCells.last().getElementsByTagName('input') ).first();

		switch ($F(selectElement))
		{
			case AccountSection.SELECT_ELEMENT_WAIVE_VALUE:
                          // TODO: perform a proper calculation!
                          amountDue -= 15.0;
			case AccountSection.SELECT_ELEMENT_ADJUST_VALUE:
                          // TODO: perform a proper calculation!
                          amountDue -= 15.0;
                          amountDue = amountDue.toString();
			case AccountSection.SELECT_ELEMENT_PAY_IN_FULL_VALUE:
				/* Hide the element */
				// Normally I'd just swap the input element's "type"
				// attribute from "text" to "hidden" but IE doesn't
				// like changing an element's "type" attribute
				// MB (2010-03-12): Ensure the payment amount is never negative
				inputElement.value = (amountDue < 0 ? '0.00' : amountDue);
				Element.hide(inputElement);

				// Set the currency value in the last cell to the amount due
				AccountSection.setCurrency(thisRowCurrencyCells.last(), inputElement.value);

				break;

			case AccountSection.SELECT_ELEMENT_SPECIFY_AMOUNT_VALUE:
				// Set the input element's value to the amount due
				// if it doesn't have a value (or if it has a non-numeric value)
				if (( $F(inputElement) == null ) || ($F(inputElement).length < 1) || ( isNaN($F(inputElement)) ))
				{
					inputElement.value = amountDue;
				}

				// Remove the text node representing the currency
				AccountSection.deleteTextNodes( thisRowCurrencyCells.last() );

				/* Show the element */

				// Normally I'd just swap the input element's "type"
				// attribute from "hidden" to "text" but IE doesn't
				// like changing an element's "type" attribute
				Element.show(inputElement);

				Field.focus(inputElement);

				break;

			case AccountSection.SELECT_ELEMENT_DONT_PAY_VALUE:

				/* Hide the element */

				// Normally I'd just swap the input element's "type"
				// attribute from "hidden" to "text" but IE doesn't
				// like changing an element's "type" attribute
                                inputElement.value = "0.00";
				Element.hide(inputElement);


				// Set the currency value in the last cell to zero
				AccountSection.setCurrency(thisRowCurrencyCells.last(), "0.00");

				break;
		}

		// Lastly, update the total
		AccountSection.updateTotal(selectElement.parentNode);

	},

	payAllAmounts : function(anchorElement)
	{
		anchorElement = $(anchorElement);
		var tableElement = anchorElement.walkUpUntil('table');
		anchorElement.id = AccountSection.PAY_ALL_FULL_ID;
		AccountSection.updateTableSelections(anchorElement, tableElement);
	},

        /* This accepts an element representing a waiver, adjustment, or
           pay-all option and updates the invoice details in the table.
           2010.01.09 Milosh Boroyevich */
        updateTableSelections : function(element, table)
        {
          element = $(element);
          table = $(table);

          // If element or table are null, exit
          if (element === null || table === null) return;

          var selectValue = null;
          // If the checkbox is selected then undo the table selections
          if (element.checked !== undefined && !element.checked) {
            // set all drop-downs to the default
            selectValue = AccountSection.SELECT_ELEMENT_PAY_IN_FULL_VALUE;
          } else {
            switch (element.id) {
            case AccountSection.PAY_ALL_FULL_ID:
              AccountSection.unsetCheckbox(AccountSection.PAY_ALL_WAIVE_ID);
              AccountSection.unsetCheckbox(AccountSection.PAY_ALL_ADJUST_ID);
              selectValue = AccountSection.SELECT_ELEMENT_PAY_IN_FULL_VALUE;
              break;
            case AccountSection.PAY_ALL_ADJUST_ID:
              AccountSection.unsetCheckbox(AccountSection.PAY_ALL_WAIVE_ID);
              selectValue = AccountSection.SELECT_ELEMENT_ADJUST_VALUE;
              break;
            case AccountSection.PAY_ALL_WAIVE_ID:
              AccountSection.unsetCheckbox(AccountSection.PAY_ALL_ADJUST_ID);
              selectValue = AccountSection.SELECT_ELEMENT_WAIVE_VALUE;
              break;
            default:
              return;
            }
          }

          var selectElements = $A( table.getElementsByTagName('select') );
          selectElements.each(function(selectElement) {
            if (element.checked) {
              selectElement.disabled = true;
            } else {
              selectElement.disabled = false;
            }
            /* Loop through all the options and find which index matches
               up to the activity's event; then set that */
            var found = false;
            var dontPayIndex = -1;
            for (i = 0; i < selectElement.options.length; i++) {
              if (selectElement.options[i].value == selectValue) {
                // check if already set
                if (selectElement.selectedIndex != i) {
                  selectElement.selectedIndex = i;
                  found = true;
                }
                // ensure selection does not change to "don't pay", since match found
                dontPayIndex = -1;
                break;
              } else if (selectElement.options[i].value == AccountSection.SELECT_ELEMENT_DONT_PAY_VALUE) {
                dontPayIndex = i;
              }
            }
            // set selection to "don't pay" if desired option not found
            if (!found && dontPayIndex >= 0 && selectElement.selectedIndex != dontPayIndex) {
                selectElement.selectedIndex = dontPayIndex;
                found = true;
            }
            if (found)
              AccountSection.populatePaymentCell(selectElement);
          }.bind(this));
        },

        /* This accepts a checkbox id and unsets the checkbox if set.
           2010.01.09 Milosh Boroyevich */
        unsetCheckbox : function(checkboxId)
        {
          var el = document.getElementById(checkboxId);
          if (el && el.checked)
            el.checked = false;
        },

	/* IE has remarkable trouble with setting tables to 100%
		width; so, this function does that manually by
		dynamically forcing tables to the widths of their parent
		elements. */

	/*
		This is currently deprecated and no longer used. With
		some other CSS modificatgions (hasLayout and such),
		it no longer became necessary.
	*/
	setTableToFullWidthIfNeeded : function(tableElement)
	{
		tableElement = $(tableElement);

		var cssWidth = tableElement.getStyle('width');

		if (cssWidth == "100%")
		{
			var parentElement = tableElement.parentNode;
			var parentWidth = parentElement.getDimensions().width;

			// Force the table element to the width of its parent
			tableElement.style.width = parentWidth + "px";
		}
	},

	/* This accepts an anchor element which, when clicked,
		sets the page to "print preview mode":

		* All "print" stylesheets are enabled for "screen" too
		* All screen-only stylesheets are disabled
	*/
	togglePrintPreviewMode : function(anchorElement)
	{
		anchorElement = $(anchorElement);

		anchorElement = anchorElement.walkUpUntil('a');

		// This classname is assigned to <link> elements
		// when their media types are been changed
		var MEDIA_TYPE_HAS_CHANGED_CLASSNAME = 'changed';

		// If no parent anchor element was found, exit
		if (anchorElement === null) return;

		// First, grab all the "link" elements
		var linkElements = $A( document.getElementsByTagName('link') );

		var isInPrintPreviewMode = false; // initialize

		// Then, loop through the link elements and rejigger them accordingly
		for (i=0, len=linkElements.length; i < len; i++)
		{
			var currentLinkElement = linkElements[i];

			// If it's rel attribute isn't "stylesheet", then it's of no interest to us
			if ((!currentLinkElement.rel) || (currentLinkElement.rel.toLowerCase() != "stylesheet"))
			{
				continue;
			}

			// Check if the given stylesheet is normally print-but-not-screen

			if (Element.hasClassName(currentLinkElement,AccountSection.NORMALLY_PRINT_BUT_NOT_SCREEN_CLASSNAME))
			{
				// In this scenario, we're dealing with a stylesheet that should be set
				// back to "print" and "screen" should be removed. In other words, we're
				// getting out of print-preview mode.

				// Remove the "screen" value from the "media" attribute
				var newMediaValue = currentLinkElement.media.match(AccountSection.ALL_MEDIA_TYPES_EXCEPT_FOR_SCREEN_REGEX);

				if (getObjectClass(newMediaValue) == "Array")
				{
					newMediaValue = newMediaValue.first();
				}

				currentLinkElement.media = newMediaValue;

				// And, remove the NORMALLY_PRINT_BUT_NOT_SCREEN_CLASSNAME class
				Element.removeClassName(currentLinkElement,AccountSection.NORMALLY_PRINT_BUT_NOT_SCREEN_CLASSNAME);

				Element.addClassName(currentLinkElement,MEDIA_TYPE_HAS_CHANGED_CLASSNAME);
			}
			else if (Element.hasClassName(currentLinkElement,AccountSection.NORMALLY_SCREEN_BUT_NOT_PRINT_CLASSNAME))
			{
				// In this scenario, we're dealing with a stylesheet that should be set
				// back to "screen" (and "print" left along). In other words, we're
				// leaving print-preview mode.

				// Add the "screen" value to the "media" attribute
				currentLinkElement.media = currentLinkElement.media + ",Screen";

				// And, remove the NORMALLY_SCREEN_BUT_NOT_PRINT_CLASSNAME class
				Element.removeClassName(currentLinkElement,AccountSection.NORMALLY_SCREEN_BUT_NOT_PRINT_CLASSNAME);

				Element.addClassName(currentLinkElement,MEDIA_TYPE_HAS_CHANGED_CLASSNAME);
			}
			else
			{
				// If it got this far, this must be the first time togglePrintPreviewMode()
				// has been called. So, add or remove media bits & classes as needed

				// Check if the current link element has "screen" but not "print"
				if (( currentLinkElement.media.match(/screen/i) ) && ( !currentLinkElement.media.match(/print/i) ))
				{
					/* In this scenario, we're going into print-preview mode */

					// Remove the "screen" value from the "media" attribute
					var newMediaValue = currentLinkElement.media.match(AccountSection.ALL_MEDIA_TYPES_EXCEPT_FOR_SCREEN_REGEX);

					if (getObjectClass(newMediaValue) == "Array")
					{
						newMediaValue = newMediaValue.first();
					}

					currentLinkElement.media = newMediaValue;

					// Add the class NORMALLY_SCREEN_BUT_NOT_PRINT_CLASSNAME
					Element.addClassName(currentLinkElement,AccountSection.NORMALLY_SCREEN_BUT_NOT_PRINT_CLASSNAME);

					isInPrintPreviewMode = true;

					Element.addClassName(currentLinkElement,MEDIA_TYPE_HAS_CHANGED_CLASSNAME);

				} // Check if the current link element has "print" but not "screen"
				else if (( currentLinkElement.media.match(/print/i) ) && ( !currentLinkElement.media.match(/screen/i) ))
				{
					/* In this scenario, we're also going into print-preview mode */

					currentLinkElement.media = currentLinkElement.media + ",Screen";

					// Add the class NORMALLY_PRINT_BUT_NOT_SCREEN_CLASSNAME
					Element.addClassName(currentLinkElement,AccountSection.NORMALLY_PRINT_BUT_NOT_SCREEN_CLASSNAME);

					isInPrintPreviewMode = true;

					Element.addClassName(currentLinkElement,MEDIA_TYPE_HAS_CHANGED_CLASSNAME);
				}

			}

		} // end of for-loop


		// First, grab all the "link" elements
		var linkElements = $A( document.getElementsByTagName('link') );

		// Loop through the link elements and remove/reapply them to the DOM
		// to force a reflow
		for (i=0, len=linkElements.length; i < len; i++)
		{
			// The conditional comments below get IE to skip this next part, since
			// IE breaks the page if it tries to run this.
			// (http://www.javascriptkit.com/javatutors/conditionalcompile.shtml)

			/*@cc_on
				continue;
			@*/

			var currentLinkElement = linkElements[i];

			if (Element.hasClassName(currentLinkElement, MEDIA_TYPE_HAS_CHANGED_CLASSNAME))
			{
				currentLinkElement.parentNode.insertBefore(currentLinkElement, currentLinkElement.nextSibling);
				Element.removeClassName(currentLinkElement, MEDIA_TYPE_HAS_CHANGED_CLASSNAME);
			}

		} // end of for-loop

		// For some reason, IE just throws up its hands, rendering a blank page,
		// if the "media" attributes for a stylesheet are diddled. So, this bit
		// just goes through each <link> element, stores the "media" attribute in
		// a backup variable, blanks that attribute, and then restores it. Whew.
		window.setTimeout(function()
		{
			// First, grab all the "link" elements
			var linkElements = document.getElementsByTagName('link');

			// Loop through the link elements and remove/reapply them to the DOM
			// to force a reflow
			for (i=0, len=linkElements.length; i < len; i++)
			{
				var currentLinkElement = linkElements[i];

				var oldMediaValue = currentLinkElement.media;
				currentLinkElement.media = "";
				currentLinkElement.media = oldMediaValue;
			}
		},1);


		// Lastly, update the link-text to say either "turn off print preview"
		// or "print preview", as appropriate
		switch (isInPrintPreviewMode)
		{
			case true:
				this.deleteTextNodes(anchorElement);

				var newAnchorTextNode = document.createTextNode(AccountSection.PRINT_PREVIEW_DISABLE_TEXT);
				anchorElement.appendChild(newAnchorTextNode);

				// Lastly, display any "print" links if there are any

				var listContainerElement = anchorElement.parentNode.parentNode;
				var printLinks = $A( document.getElementsByClassName(AccountSection.PRINT_ANCHOR_CLASSNAME, listContainerElement) );

				printLinks.each(Element.show);

				break;

			case false:
				this.deleteTextNodes(anchorElement);

				var newAnchorTextNode = document.createTextNode(AccountSection.PRINT_PREVIEW_ENABLE_TEXT);
				anchorElement.appendChild(newAnchorTextNode);

				// Lastly, hide any "print" links if there are any

				var listContainerElement = anchorElement.parentNode.parentNode;
				var printLinks = $A( document.getElementsByClassName(AccountSection.PRINT_ANCHOR_CLASSNAME, listContainerElement) );

				printLinks.each(Element.hide);

				break;
		} // end of switch-statement

	},

	// This recursively looks through the DOM for an input, select, or textarea
	// element and focuses the first one.
	focusFirstFormField : function(parent)
	{

		for (var i = 0, len = parent.childNodes.length; i < len; i++) {

			var currentNode = parent.childNodes[i];

			/*
				This makes sure that the element is visible before doing any
				further checking. Naturally, this also makes use of short-circuit
				evaluation since isVisible would fail if the currentNode wasn't
				an an element ("1" = element node)
			*/
			if ((currentNode.nodeType == 1) && (isVisible(currentNode)) )
			{
				var currentNodeTagName = currentNode.tagName.toLowerCase();

				switch (currentNodeTagName)
				{
					case 'input':

						var inputElementType = currentNode.type.toLowerCase();

						/*
							Note to self: If desired, the code could check
							to see if the currentNode is, say, a search field
							(or some other field that might hypothetically
							appear on every page) and skip over that
							particular field since, in the case of a
							content-page which happens to have a search
							field, the user wouldn't be able to scroll
							the page since the focus would be in the
							input field.
						*/

						switch (inputElementType)
						{
							case 'radio':
								if (currentNode.checked)
								{
									Field.activate(currentNode);
									return true;
								}
								break;

							// Don't focus it if the node is an image-type submit button
							case 'image':
								break;

							// Don't focus it if the node is a regular submit button, either
							case 'submit':
								break;

							case 'hidden':
                                                                break;

							default:
								// Make sure it's not the search box since it'd be kinda
								// dumb to focus that
								if (
										((currentNode.id) && ( currentNode.id.match(/search/i) )) ||
										((currentNode.name) && ( currentNode.name.match(/search/i) ))
									)
								{
									break;
								}

								Field.activate(currentNode);
								return true;
								break;

						} // end of nested switch statement

						break;

					case 'select':
						Field.activate(currentNode);
						return true;
						break;

					case 'textarea':
						Field.activate(currentNode);
						return true;
						break;
				}

				/*
					If the current node isn't a form-related element
					(which would be the case if it got this far) and
				 	if the node has children, dive into them
				*/
				if (currentNode.childNodes.length)
				{
					if ( this.focusFirstFormField(currentNode) ) return true;
				}

			} // end of if-statement for element-nodes

		} // end of for-loop

		// If it got this far, no form fields were found in the current tree, so return false
		return false;
	},



	// Add a new credit card row inside credit card table list
	addNewCreditCard: function(creditCardForm, tbody, defaultBilling)
	{


		var elements = creditCardForm.elements;
		// Insert a row in the table at the last row
		var newRow   = tbody.insertRow(tbody.rows.length);
		// Insert a cell in the row at index 0
		var newCell  = newRow.insertCell(0);
		// Append a text node to the cell
		var newText  = document.createTextNode(elements['name-on-credit-card'].value);
		newCell.appendChild(newText);


		newCell  = newRow.insertCell(1);
		// Append a text node to the cell
		newText  = document.createTextNode(elements['cardType'].options[elements['cardType'].selectedIndex].innerText);
		newCell.appendChild(newText);


		newCell  = newRow.insertCell(2);
		// Append a text node to the cell
		removeUnwantedChar(elements['card-number']);
		newText  = document.createTextNode(elements['card-number'].value);
		newCell.appendChild(newText);


		newCell  = newRow.insertCell(3);
		// Append a text node to the cell
		newText  = document.createTextNode(elements['card-expiration-month'].value + '/' + elements['card-expiration-year'].value);
		newCell.appendChild(newText);

		newCell  = newRow.insertCell(4);
		// Append a text node to the cell
		var cbEl  = document.createElement('input');
		cbEl.type = 'checkbox';
		cbEl.checked = defaultBilling;
		newCell.appendChild(cbEl);

		this.resetCreditCardForm(creditCardForm);
	},


	// Add a new credit card row inside credit card table list
	resetCreditCardForm: function(creditCardForm)
	{
		creditCardForm.elements['name-on-credit-card'].value = '';
		creditCardForm.elements['cardType'].selectedIndex = 0;
		creditCardForm.elements['card-number'].value = '';
		creditCardForm.elements['card-expiration-month'].selectedIndex = 0;
		creditCardForm.elements['card-expiration-year'].selectedIndex = 0;
	}


}; // end of AccountSection object


var AccountSectionRules = {

	/* When someone clicks the logo or one of the footer linkss within the signup process,
		he or she is presented with a confirmation message first. */
	'body.sign-up-section a#logo, body.sign-up-section div#footer a' : function(element) {

		element.onclick = function()
		{
			return confirm(AccountSection.SIGNUP_LEAVING_CONFIRMATION_TEXT);
		}

		element = null; // prevent memory leaks

	},

	'body.account-section div#content dl dd label#personal-label input' : function(element) {
		if (element.checked)
		{
			/* Sets the "Account Information" area to Personal mode */
			AccountSection.setAsPersonalMode();
		}
	},

	'body.account-section div#content dl dd label#business-label input' : function(element) {
		if (element.checked)
		{
			/* Sets the "Account Information" area to Business mode */
			AccountSection.setAsBusinessMode();
		}
	},

	/* Sets the "Account Information" area to Personal mode */
	'body.account-section div#content dl dd label#personal-label input:click' : AccountSection.setAsPersonalMode,

	/* Sets the "Account Information" area to Business mode */
	'body.account-section div#content dl dd label#business-label input:click' : AccountSection.setAsBusinessMode,


	'body.account-section fieldset p#non-us-address-field input' : function(element) {

		AccountSection.setAsDomesticOrNonUSAddress(element.checked);

		element = null;
	},

	'body.account-section fieldset p#non-us-address-field input:click' : function(element) {

		AccountSection.setAsDomesticOrNonUSAddress(element.checked);

		// If actually clicked on the element (as opposed to the page
		// loading normally), focus the "Address Line 1 field" if
		// th eelement isn't checked

		if (!element.checked)
		{
			Field.focus(AccountSection.INPUT_ELEMENT_ADDRESS_LINE_1_ID);
		}
		else
		{
			Field.focus(AccountSection.SELECT_ELEMENT_COUNTRY_ID)
		}

		element = null;
	},

	'body.account-section fieldset dd#country-dd select, body.account-section fieldset dd#country-dd select:change' : function(element) {

		if ($F(element) == "USA")
		{
			/* Sets the address area back to domestic mode */
			AccountSection.setAsDomesticAddress();

			// Set the checkbox for "My billing address is outside the US"
			// back to unchecked, just in case
			$(AccountSection.INPUT_ELEMENT_NON_US_ADDRESS).checked = false;

			Field.focus(AccountSection.INPUT_ELEMENT_ADDRESS_LINE_1_ID);
		}

	},

	'body.account-section div#content fieldset dl dd p.temporary-license-plate-selection input, body.account-section div#content fieldset dl dd p.temporary-license-plate-selection input:click' : function(element) {

		/* Displays the adjacent help if the element is checked */
		AccountSection.showAdjacentHelp(element);

		/* Set the text field to "TEMP" and disable it if the element is checked */

		AccountSection.setAsTemporaryLicensePlate(element);
	},

	'body.account-section div#content-container form:submit' : function(element, event) {

		/* Checks if there's a legal-checkbox on the page and disables
			the form if	it's not checked */

		window.setTimeout(function()
        {
			AccountSection.checkForLegalAgreement(event);

		}.bind(this),2000);
	},

	/*'body.account-section form p#agree-to-license-agreement-field input, body.account-section form p#agree-to-license-agreement-field input:click' : function(element) {

		var submitButton = $('review-confirmation-page');
		var submitIsDisabled = (submitButton.src.indexOf('disabled') != -1);

		if (element.checked)
		{
			// If the checkbox is checked but the button is disabled..."
			if (submitIsDisabled)
			{
				submitButton.src = submitButton.src.replace(/-disabled/,"");
			}
			submitButton.disabled = false;
		}
		else
		{
			// If it's not checked but the button is enabled...
			if (!submitIsDisabled)
			{
				/*
					The regular expression below searches for:

					* a period (".") followed by
					* ... one or more letters (such as "gif") followed by
					* ... the end of the string

					[Then, it replaces the part just before the extension with "-disabled"]


				submitButton.src = submitButton.src.replace(/(\.[a-zA-Z]+)$/,"-disabled$1");
				submitButton.disabled = true;
			}
		}

		element = null;
	},

	/* for IE issue */

	'body.account-section form p#agree-to-license-agreement-field input' : function(element) {




        var submitButton = $('review-confirmation-page');
		var submitIsDisabled = (submitButton.src.indexOf('disabled') != -1);

		if (element.checked)
		{
			// If the checkbox is checked but the button is disabled..."
			if (submitIsDisabled)
			{
				submitButton.src = submitButton.src.replace(/-disabled/,"");
			}
			submitButton.disabled = false;
		}
		else
		{
			// If it's not checked but the button is enabled...
			if (!submitIsDisabled)
			{
				/*
					The regular expression below searches for:

					* a period (".") followed by
					* ... one or more letters (such as "gif") followed by
					* ... the end of the string

					[Then, it replaces the part just before the extension with "-disabled"]
				*/

				submitButton.src = submitButton.src.replace(/(\.[a-zA-Z]+)$/,"-disabled$1");
				submitButton.disabled = true;
			}
		}

		element = null;

	},


	'body.account-section form p#agree-to-license-agreement-field input:click' : function(element) {


		var submitButton = $('review-confirmation-page');
		var submitIsDisabled = (submitButton.src.indexOf('disabled') != -1);

		if (element.checked)
		{
			// If the checkbox is checked but the button is disabled..."
			if (submitIsDisabled)
			{
				submitButton.src = submitButton.src.replace(/-disabled/,"");
			}
			submitButton.disabled = false;
		}
		else
		{
			// If it's not checked but the button is enabled...
			if (!submitIsDisabled)
			{
				/*
					The regular expression below searches for:

					* a period (".") followed by
					* ... one or more letters (such as "gif") followed by
					* ... the end of the string

					[Then, it replaces the part just before the extension with "-disabled"]
				*/

				submitButton.src = submitButton.src.replace(/(\.[a-zA-Z]+)$/,"-disabled$1");
				submitButton.disabled = true;
			}
		}

		element = null;

	},
	/* This takes care of all the event-handler bits for the
		Authorized Contact fields, including rejiggering Enter so
		that it triggers "Add Another" rather than delete, and
		hooking up the action for the "Delete" buttons */
	'body.account-section form dd#authorized-contacts li' : function(element) {
		AccountSection.applyEventHandlersToAuthorizedContactsFields(element);
	},

	/* This is used for the Authorized Contacts section of the Account Information
		page -- when the "Add another contact" button is clicked, this adds another field */
	'body.account-section div#content form dd#authorized-contacts input.add-another-contact' : function(element) {
		element.onclick = function()
		{
			AccountSection.addAnotherField(this.parentNode);
			return false;
		}

		element = null;
	},

	/* If a vehicle has outstanding tolls, it would trigger version
		of the Vehicle Information page with an embedded (but invisible)
		lightbox -- by including the class "Immediate Lightbox" on the
		body element, the lightbox is shown as soon as the page loads. */
	'body.account-section.vehicle-information-section.immediate-lightbox' : function () {

		/* This makes use of addEvent() -- rather than running right
			away, for example, because the ibox-initialization is
			only run on document-load and this wouldn't even
			work if it ended up running before the ibox-initialization
			finished. */

		addEvent(window, 'load', function()
		{
			// This nested 10ms delay helps avoid race conditions in IE

			window.setTimeout(function()
			{
				// Hide select element since they
				// end up bleeding through with IE
				AccountSection.hideSelectElements();

				// This simulates the initialize-ibox code from around
				// lines 32-40 from ibox.js
				var params =
					{
						width: 550,
						height: 500,

						isStaticIBox: true
					};

				// Element to lightbox-ify
				var url = "#outstanding-tolls";

				var title = "Outstanding Tolls";

				if(showIbox(url,title,params)) {
					showBG();
					window.onscroll = maintPos; // "maintPos" is a function, btw
					window.onresize = maintPos; // "maintPos" is a function, btw
				}
			}.bind(this), 10);
		});

	},

	/* The "billing information section" pertains to a page in the sign-up process, while the
		"payment section" refers to a page in the violations process */
	'body.account-section.billing-information-section, body.account-section.payment-section' : function() {
		// add the listener to unFocus.History
		unFocus.History.addEventListener('historyChange', AccountSection.setBillingType);

		AccountSection.setBillingType(unFocus.History.getCurrent());
	},

	'body.account-section a#credit-card-link' : function(element) {

//    	element.onclick = function() {
//
//			/* The unFocus History Keeper helps preserve the
//				effect of the back button in Ajax / DOM Scripting
//				scenarios. */
//
//			/* Note: The accessibility-target element within the page
//				(in this case, "credit-card") must /not/ have the same
//				identifyer as what's added to the history (in this case,
//				"credit-card-choice"). Otherwise, IE gets a little
//				confused when going back. */
//			unFocus.History.addHistory('credit-card-choice');
//			document.getElementById("paymentType").value="credit";
//			return false;
//		}
//
//		element = null;
	},

	'body.account-section a#withdraw-funds-link' : function(element) {

//		element.onclick = function() {
//
//			/* The unFocus History Keeper helps preserve the
//				effect of the back button in Ajax / DOM Scripting
//				scenarios. */
//
//			/* Note: The accessibility-target element within the page
//				(in this case, "withdraw-funds") must /not/ have the same
//				identifyer as what's added to the history (in this case,
//				"withdraw-funds-choice"). Otherwise, IE gets a little
//				confused when going back. */
//			unFocus.History.addHistory('withdraw-funds-choice');
//			document.getElementById("paymentType").value="eft";
//			return false;
//		}
//
//		element = null;
	},

	/* Show/hide bits for "Alternate Credit Card" on the Billing page */

	'body.account-section div#content fieldset p.alternate-credit-card-selection input, body.account-section div#primary-content fieldset p.alternate-credit-card-selection input' : function (element) {
		AccountSection.showOrHideAlternateCreditCardFields(element.checked);

	},

	'body.account-section div#content fieldset p.alternate-credit-card-selection input:click, body.account-section div#primary-content fieldset p.alternate-credit-card-selection input:click' : function(element) {

		AccountSection.showOrHideAlternateCreditCardFields(element.checked);
		Field.focus(AccountSection.INPUT_ELEMENT_NAME_ON_ALTERNATE_CREDIT_CARD);
	},

	/**	this requirement was deleted. I've kept the code here in case it comes back - 11/02/2006  **/
	/* Show/hide bits for "Alternate EFT Account" on the Billing page */
	/*
	'body.account-section fieldset p.alternate-eft-account-selection input' : function (element) {
		AccountSection.showOrHideAlternateEFTAccountFields(element.checked);

	},

	'body.account-section fieldset p.alternate-eft-account-selection input:click' : function(element) {

		AccountSection.showOrHideAlternateEFTAccountFields(element.checked);
		Field.focus('personal-bank-account-2');
	}, */


	'body.account-section div#content div.section p.billing-address-is-different-from-mailing-selection input, body.account-section div#primary-content div.section p.billing-address-is-different-from-mailing-selection input' : function (element) {
		AccountSection.showOrHideBillingAddressFields(element.checked);

	},

	'body.account-section div#content div.section p.billing-address-is-different-from-mailing-selection input:click, body.account-section div#primary-content div.section p.billing-address-is-different-from-mailing-selection input:click' : function(element) {

		AccountSection.showOrHideBillingAddressFields(element.checked);
		Field.focus(AccountSection.INPUT_ELEMENT_NON_US_ADDRESS);
	},

	/* In the Manage area, hide the paragraph containing the "Make this my primary credit card for EZ Account rebilling"
		checkbox by default. Then, if someone makes a change to one of the text fields, it'll be unhidden. */
	'body.manage-section div#credit-card p#primary-card-for-rebilling-field' : function(element) {

		Element.hide(element);

	},

	/* In the Manage area, hide the paragraph containing the "Update my billing address using this information."
		checkbox by default. Then, if someone makes a change to one of the text fields, it'll be unhidden. */
	'body.manage-section fieldset#billing-address-fields p#update-billing-address-field' : function(element) {

		Element.hide(element);

	},

	/* In the Manage area, this un-hides the paragraph containing the
		"Make this my primary credit card for EZ Account rebilling"
		checkbox if/when someone enters text in one of the Credit Card
		fields. This makes use of onkeypress rather than onchange to allow it
		to react as soon as a change is made (rather than requiring someone
		to move focus outside the field first). */
	'body.manage-section div#credit-card div.section fieldset dl input' : function(element, event) {

		element.onkeydown = function(event)
		{
			 return Common.runFunctionIfDestructiveKeydown(event, function() {

				AccountSection.showMakeThisMyPrimaryCardParagraphIfNeeded();

			 });
		}

	},

	/* This does the same as above but for the select elements in that area */
	'body.manage-section div#credit-card div.section fieldset dl select:change' : function(element) {

		AccountSection.showMakeThisMyPrimaryCardParagraphIfNeeded();

	},

	/* In the Manage area, this un-hides the paragraph containing the
		"Update my billing address using this information."
		checkbox if/when someone enters text in one of the Billing Address
		fields. This makes use of onkeypress rather than onchange to allow it
		to react as soon as a change is made (rather than requiring someone
		to move focus outside the field first). */
	'body.manage-section fieldset#billing-address-fields dl input' : function(element, event) {

		element.onkeydown = function(event)
		{
			 return Common.runFunctionIfDestructiveKeydown(event, function() {

				AccountSection.showUpdateMyBillingAddressParagraphIfNeeded();

			 });
		}

	},

	/* This does the same as above but for the select elements in that area */
	'body.manage-section fieldset#billing-address-fields dl select:change' : function(element) {

		AccountSection.showUpdateMyBillingAddressParagraphIfNeeded();

	},


	'body.account-section div#content fieldset.legal a.legal-document, body.account-section div#primary-content fieldset.legal a.legal-document' : function(element) {
		element.onclick = function() {
			window.open(this.href, '', 'width=770,height=550,resizable=yes,scrollbars=yes');
  			return false; // prevents the link from firing
		}
		element = null; // prevent memory leaks

	},

	/*RFC20130184 */

	'body.account-section div#content fieldset.agencyinfo a.legal-document, body.account-section div#primary-content fieldset.agencyinfo a.legal-document' : function(element) {
		element.onclick = function()
		{
		    window.open(this.href, '', 'width=400,height=500,left=200,top= 200, resizable=yes,scrollbars=yes');
  			return false; // prevents the link from firing
		}
		element = null; // prevent memory leaks
	},




	/* Provides pop-up for alternate EZ TAGs account number references
		[MWK / 2006-09-22] */

	'body.account-section div#content a.ez-tag-references' : function(element) {
		element.onclick = function() {
			window.open(this.href, '', 'width=770,height=550,resizable=yes,scrollbars=yes');
			return false; // prevents the link from firing
		}
		element = null; // prevent memory leaks
	},

	/* This applies a popup window effect for the Payment Plan Details link */
	'body.account-section.open-violations-section div#content dl.alerts a' : function(element) {
		element.onclick = function() {
			window.open(this.href, '', 'width=770,height=550,resizable=yes,scrollbars=yes');
  			return false; // prevents the link from firing
		}
		element = null; // prevent memory leaks
	},


	'body.account-section div#content ul.vehicles input.delete' : function(element) {
		element.onclick = function() {

			var confirmationResult = confirm("Are you sure you want to delete this?");

			/* Note to self: Uncomment this line for production use */
			// return confirmationResult;

  			return false; // <-- Just for the sake of the prototypes, return false anyway
		}
		element = null; // prevent memory leaks
	},

	/* This bit of code applies a visual effect to updated elements
		(such as elements that were just changed via a round-trip to preferences) */
	'body.account-section div#content dl.updated' : function(element) {

		// This adds an intentional delay to the highlight effect
		// to help ensure that the page has loaded before it occurs
		window.setTimeout(function()
		{
			var highLightParameters =
			{
				startcolor: '#ffff99', /* Light yellow */
				endcolor: '#F2ECF6', /* Light purple - the background color */
				restorecolor: '#F2ECF6', /* Light purple - the background color */
				duration: 2.0
			};

		 	new Effect.Highlight(element, highLightParameters);
		}.bind(this), 2000);


	 },

	// When a checkbox in a select-all row is clicked, clickify the other
	// checkboxes as needed
	 'body.account-section div#content tr.select-all input.checkbox:click, body.account-section div#content table.payments thead input.checkbox:click' : function(element) {

		 AccountSection.selectAll(element);

		 element = null;

	 },

	 /*
	 	When the page is loaded -- and also when any of the checkboxes in a payment
	 	table are clicked -- propagate the monetary amounts to the
	 	[Selected] Payment(s) column
	 */
	 'body.account-section div#content table.payments tbody select, body.account-section div#content table.payments tbody select:change' : function(element) {

		 AccountSection.populatePaymentCell(element);

		 element = null;
	 },

	 /* This applies behavior to the "Pay All Amounts" link within Payments tables */
	 'body.account-section div#content table.payments thead th#pay-all-invoices-header a' : function(element)
	 {
		// The actual link is hidden by default, so that
		// users without JavaScript don't even see it. This
		// then unhides it.
		Element.show(element);

		element.onclick = function()
		{
			AccountSection.payAllAmounts(this);
			return false;
		}
	 },

        /* This applies behavior to the waiver and adjustment checkbox controls.
TODO: however, this is not being allocated correctly!!!
           2010.01.09 Milosh Boroyevich */
        'body.account-section div#content discount-control input' : function(element)
        {
          element.onclick = function() {
            var nearestTable = AccountSection.findNearestTable(this);
            AccountSection.updateTableSelections(this, nearestTable);
          }
          element = null; // avoid memory leaks
        },

	 'body.account-section div#content ul.table-controls li.expand-control a' : function(element) {

		element.onclick = function()
		{
			var nearestTable = AccountSection.findNearestTable(this);
			AccountSection.expandOrCollapseAllTBodyChildren(nearestTable, AccountSection.EXPANDED_CLASSNAME);

			return false; // prevent the link from firing
		}

		element = null; // avoid memory leaks

	},

	'body.account-section div#content ul.table-controls li.collapse-control a' : function(element) {

		element.onclick = function()
		{
			var nearestTable = AccountSection.findNearestTable(this);
			AccountSection.expandOrCollapseAllTBodyChildren(nearestTable, AccountSection.COLLAPSED_CLASSNAME);

			return false; // prevent the link from firing
		}

		element = null; // avoid memory leaks

	},

	 /* This sets all the tbody elements in a payments-table to "collapsed" as soon as the table loads;
	 	the tbody elements, by default, are "expanded" for the sake of users that may not have JavaScript */
	 'body.account-section div#content table.payments' : function(element) {

		 var paymentsTable = $(element);

		 AccountSection.expandOrCollapseAllTBodyChildren(paymentsTable, AccountSection.COLLAPSED_CLASSNAME);

		 return;

		 var paymentsTableTBodyElements = $A( paymentsTable.getElementsByTagName('tbody') );

		 /* Loop through all tbody elements and, for any that have the class "expanded",
		 	remove that class and add the class "collapsed" */
		 paymentsTableTBodyElements.each(function(tbodyElement) {
			 if (tbodyElement.hasClassName(AccountSection.EXPANDED_CLASSNAME))
			 {
				 AccountSection.expandOrCollapseTBody(tbodyElement, AccountSection.COLLAPSED_CLASSNAME);
			 }
		 }.bind(this));
	 },

	 /* This deactivates the default action of links witin an invoice-summary row (which
	 	affects only the the invoice numbers) */
	 'body.account-section div#content table.payments tbody tr.invoice-summary a' : function(element) {

		element.onclick = function()
		{
			return false;
		}

		element = null;
	 },

	 /* This applies the expand/collapse functionality to the rows within an payments-table */
	 'body.account-section div#content table.payments tbody tr:click' : function(element, event) {

		 AccountSection.processTBodyClickEventHandler(element, event);
	 },

	 /* This dynamically updates the totals when a key is pressed within a textfield
	 	in a Payments table. (The "fieldset.add-to-ez-account-fields" bit is the textfield
	 	allowing users to add additional funds to their EZ Accounts.) */
	 'body.account-section div#content table.payments tbody input.textfield, body.account-section div#content fieldset.add-to-ez-account-fields input.textfield' : function(element, event) {

		 element.onkeypress = function(event)
		 {
			 return AccountSection.processPaymentsTableTextfieldKeys(this, event);
		 }

		 element.onkeydown = function()
		 {
			 // The minute delay here is necessary since
			 // the DOM isn't updated immediately upon keypress,
			 // as it would appear.

			 window.setTimeout(function() {
				AccountSection.updateTotal(this);
			}.bind(this), 10);
		 }

		 element.onblur = function()
		 {
			 this.value = this.value.toCurrency();
			 return AccountSection.updateTotal(this);
		 }

		 element = null; // prevent memory leaks

	 },

	'body.account-section dl#introduction dd.print' : function(element) {

		// First, make sure there's not a "print-preview" link since,
		// if there was, this would need to stay hidden
		if ( !$$('body.account-section a.print-preview').length )
		{
			/* The "print" links are hidden by default, for
				browsers that don't support JavaScript. Naturally,
				this un-hides those links */
			Element.show(element);
		}
	},

	/* The first selector (with "dd.print a") is the traditional "print" link
		while the second selector (wuith "a.print") is used on pages
		which also have a print-preview link, such as on the Manage pages */
	'body.account-section dl#introduction dd.print a:click, body.account-section ul#page-controls a.print:click' : function(element, event) {
		if (!event) var event = window.event;

		window.print();
		Event.stop(event);
		return false;
	},

	/* IE has remarkable trouble with setting tables to 100%
		width; so, this function does that manually by
		dynamically forcing tables to the widths of their parent
		elements. */
	// 'body.account-section table.data-table' : function(element) {

		// If it comes down to it, this could be called to fix data tables
		// in IE. It's currently disabled, though, as it's not
		// necessary at the moment.

		/* if (/MSIE/.test(navigator.userAgent))
		{
			AccountSection.setTableToFullWidthIfNeeded(element);
		} */
	// },

	/* If the page has a form, this focuses the first form field */
	'body.account-section form' : function(element) {

		// First check to make sure that the form has at least one text field.
		// Not that this only works with text fields -- it works with radio
		// buttons and such too -- but a form probably isn't a "data form"
		// unless it has at least one text field.

		var documentInputFields = element.getElementsByTagName('input');

		for (var i=0, len=documentInputFields.length; i < len; i++)
		{
			var currentInputField = documentInputFields[i];

			// Make sure it's not the search box since it'd be kinda
			// dumb to focus that
			if (
					(currentInputField.type == "text") &&
					(!currentInputField.id.match(/search/i) ) &&
					(!currentInputField.name.match(/search/i) ) &&
					(isVisible(currentInputField))
				)
			{
				AccountSection.focusFirstFormField(element);
				break;
			}

		} // end of for-loop

	},

	'body.account-section ul#page-controls a.print-preview' : function(element) {

		// Display the element in case it was hidden by default
		// (so as to not even present it to non-JavaScript browsers)
		Element.show(element);

		element.onclick = function()
		{
                        AccountSection.togglePrintPreviewMode(this);

			return false; // prevent the link from firing
		}

		element = null; // prevent memory leaks

	},


	'body.account-section div#creditCardList p.buttons input#addCC:click' : function(element) {
		var form = document.forms['billingInfoForm'];
		doPaypageSubmit(function(respObj){
        	document.getElementById('response$paypageRegistrationId').value = respObj.paypageRegistrationId;
        	form.action = 'signupBillingPAN.do';
    		if (form.action.indexOf("accountInformation")>-1)
    		{
    			form.action = 'dispChangePaymentMethod.do?dispatchAction=addNew';
    		}
    		else
    		{
    			form.action = 'signupBillingPAN.do';
    		}
    		form.submit();
        });
        return false;
	},


	'body.account-section div#creditCardList p.buttons input#resetCC:click' : function(element) {
		AccountSection.resetCreditCardForm();
	},


	'body.account-section div#creditCardList table.data-table tbody input:click' : function(element) {
		var rowIndex = element.getAttribute("rowIndex");
		var form = document.forms['billingInfoForm'];
		if (form.action.indexOf("accountInformation")>-1)
		{
			form.action = 'dispChangePaymentMethod.do?dispatchAction=changeDefaultBilling&index='+rowIndex;
		}
		else
		{
			form.action = 'signupBillingPAN.do?dispatchAction=changeDefaultBilling&index='+rowIndex;
		}
		form.submit();
	}



};

/*
	Event.observe is overloaded to fire as soon as the DOM is ready,
	via the domready-onload.js script.

	http://www.agileweb.org/articles/2006/06/16/speeding-up-onload
*/

/* This checks if Rules is already defined; If so, it simply
	extends that object. If not, it sets Rules to AccountSectionRules
	and adds an event handler for that */

if (typeof Rules != "undefined")
{
	/*
		Object.extend() "copies all properties from the source to the destination object.
		Used by Prototype to simulate inheritance (rather statically) by copying to prototypes."

		http://www.prototypejs.org/api/object/extend
	*/

	var Rules = Object.extend(AccountSectionRules, Rules);
}
else
{
	var Rules = AccountSectionRules;

	DOMReady.addFunctionToQueue( function()
	{
		EventSelectors.start(Rules);
	});
}
