/* This speeds up onload funtions by firing them as soon as
	the DOM is ready.

	Example use:

		DOMReady.addFunctionToQueue( function()
			{
				// function code here
			}
		);

	Based on Dean Edwards' DOMContentLoaded script:
	http://dean.edwards.name/weblog/2006/06/again/
*/


// Dean Edwards/Matthias Miller/John Resig

var DOMReady = 
{
	functionsToRun: [],
	hasAlreadyFired: false,

	_timer: null,

	initialize: function()
	{
		if (!DOMReady.hasAlreadyFired)
		{
			DOMReady.checkMozillaAndOpera();
			DOMReady.checkIE();
			DOMReady.checkSafari();
		}
		
		/* for other browsers */
		DOMReady.setOnloadEventHandler();
	},

	runFunctions: function() 
	{
    	// quit if this function has already been called
	    if (DOMReady.hasAlreadyFired) return;

	    // flag this function so we don't do the same thing twice
	    DOMReady.hasAlreadyFired = true;

	    // kill the timer
	    if (DOMReady._timer) clearInterval(DOMReady._timer);

	    // do stuff
		for (var i=0, len=DOMReady.functionsToRun.length; i < len; i++)
		{
			DOMReady.functionsToRun[i]();
		}
	
	},
	
	addFunctionToQueue: function(functionToAdd)
	{
		if (DOMReady.hasAlreadyFired)
		{
			functionToAdd();
		}
		else
		{
			DOMReady.functionsToRun.push(functionToAdd);			
		}
	},

	checkMozillaAndOpera: function() 
	{
		/* for Mozilla/Opera9 */
		if (document.addEventListener) {
		    document.addEventListener("DOMContentLoaded", DOMReady.runFunctions, false);
		}
	},

	checkIE: function()
	{
		/* for Internet Explorer */
		/*@cc_on @*/
		/*@if (@_win32)
		    document.write("<script id=__ie_onload defer src=//:><\/script>");
		    var script = document.getElementById("__ie_onload");
		    script.onreadystatechange = function() {
		        if (this.readyState == "complete") {
		            DOMReady.runFunctions(); // call the onload handler
		        }
		    };
		/*@end @*/
	},

	checkSafari: function()
	{
		/* for Safari */
		if (/WebKit/i.test(navigator.userAgent)) { // sniff
		    DOMReady._timer = setInterval(function() {
		        if (/loaded|complete/.test(document.readyState)) {
		            DOMReady.runFunctions(); // call the onload handler
		        }
		    }, 10);
		}
	},
	
	// Set up the onload event handler -- this way, even browsers
	// that don't support DOM-loaded detection will still run the functions
	setOnloadEventHandler: function() {
		var _this = this; // Closure
		this.addEvent(window, "load", function() {
			_this.runFunctions();
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

}; // end of DOMReady

DOMReady.initialize();