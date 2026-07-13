/* 
	Here're the steps to enable the Loading Message for a given page:
	
	1. Include a reference to this JavaScript file in the <head></head> of 
		the document.
		
	2. If desired, the message may be edited (via the 'message' variable below).
	
	That's it :).
	
*/


// Based in part on ideas from here:
// http://javascript.internet.com/css/pre-loading-message.html

var LoadingMessage = {
	
	message: "Please wait while the page loads&hellip;",
	
	LOADING_ELEMENT_ID: 'loading',

	// This sets up the event handler to 
	// hide the loading-message upon page load
	initialize: function() {
	
		this.addEvent(window, 'load', function() {
			
			// If the Script.aculo.us Effects library has loaded, 
			// make use of that; otherwise, just hide the element
			if (typeof Effect != "undefined")
			{
				new Effect.Fade(LoadingMessage.LOADING_ELEMENT_ID, { duration: 0.25});
			}
			else
			{
				document.getElementById(LoadingMessage.LOADING_ELEMENT_ID).style.display = 'none';
			}
		});
		
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
};

/* 
	Document.write is normally a bad idea but, not to worry, this is 
	The Single Correct Use of Document.Write :).
	http://www.quirksmode.org/blog/archives/2005/06/three_javascrip_1.html#link4
*/
document.write('<div id="' + LoadingMessage.LOADING_ELEMENT_ID + '"><p>' + LoadingMessage.message  + '</p></div>');

// Set up the window.onload event handler to hide the message when the page has finished loading
LoadingMessage.initialize();
