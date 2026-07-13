
/* Here're the steps, markup-wise, to enable table sorting on a given table.

	First off, here's some example markup which you can use to follow along with the
	steps:
	
	-------------
	Sample Markup
	-------------

		<table summary="At some point" class="data-table vehicles-and-ez-tags sortable sorted-by-nickname ascending">
			<thead>
				<tr>
					<!-- The cell with the class "sorting-tiebreaker" acts as a tiebreaker in the event that
						the user sorts by one of the columns and there's a tie between two values -->
					<th scope="col" class="nickname-header sorting-tiebreaker sorted-by-this-column"> <span class="accessibility sorted-by">Sorted by</span> Nickname</th>
					<th scope="col" class="ez-tag-header">EZ TAG #</th>
					<th scope="col" class="license-plate-header">Lic Plate</th>
					<th scope="col" class="state-header">State</th>
					<th scope="col" class="year-header">Year</th>
					<th scope="col" class="make-header">Make</th>
					<th scope="col" class="model-header">Model</th>
					<th scope="col" class="color-header">Color</th>
				</tr>
			</thead>
		
			<tbody>
				<tr class="transaction-type-toll first-in-group">
					<th scope="row" class="date-time-2006-10-16T10:39">Oct 16</th>
					<td class="time-10:39">10:39 AM</td>
					<td>Plaza 2 - Keller Springs</td>
					<td class="date-time-2006-10-16T12:50">10/16/06 12:50</td>
					<td>Mark&rsquo;s Accord</td>
					<td>Toll</td>
					<td class="currency">-$0.60</td>
				</tr>
				
				<!-- other rows here-->
				
			</tbody>
		
		</table>
		
	-----
	Steps 
	-----

	1. Make sure the table, in its default state, is sorted by /something/

	2. Add a reference to this JavaScript file and a reference to prototype.js 
		in the <head></head>. You can get Prototype.js here if you don't have
		it already: http://dev.rubyonrails.org/browser/trunk/railties/html/javascripts/prototype.js?format=txt
		
	3. You'll also need the functions getObjectClass() and walkUpUntil() 
		(which are currently in Common.js)

	4. Add the class "sortable" to the <table>.
	
	5. If the default table sorting is ascending, add the "ascending" class
		to the <table>. Else, add the "descending" class. 
		
	6. Add a unique-per-table class to each cell in the <thead>, each of which 
		with the ending "-header" (such as "date-header" or "time-header"). This 
		is used internally to keep track of which column is getting sorted.
		
		Naturally, it's fine to have non-unique values from one table to the next,
		such as having "date-header" in two different tables.
		
	7. Back to the <table> tag, add "sorted-by-###", where "###" is the column
		for which the table is sorted by default (minus the "-header" bit). For 
		instance, if it's sorted by "date" by default (i.e. sorted by the <thead>
		cell "date-header"), you'd add the class "sorted-by-date" to the <table>.
		
	8. Back to the <thead> cells, add the class "sorted-by-this-column" to the 
		column-header representing the default sort order.
		
	9. For the sake of accessibility, be sure to include this snippet of code in 
		the <thead> cell by which the table is sorted by default:
		<span class="accessibility sorted-by">Sorted by</span>
		
		For example:
		<th scope="col" class="nickname-header sorted-by-this-column"> <span class="accessibility sorted-by">Sorted by</span> Nickname</th>
		
	10.	In order for the previous item to work properly (the "accessibility" class bits), you'll
		need to make sure that you have this CSS defined:
	
		p.accessibility, ul.accessibility, li.accessibility, label.accessibility, span.accessibility
		{
			position: absolute;
			font-size: 0;
			left: -1000px;
		}
		
	11. For the individual cells within the <tbody>, two data types need some special
		attention, dates and times (or a combination date/time).
		
		[All the date/time bits below refer to ISO date format. So, if you're
		already familiar with that, you're halfway there.]
		
		For dates, add the class "date-time-yyyy-mm-dd" to any <th> (or <td>)
		cells within <tbody> that are dates. Naturally, the actual year would
		be substituted for "yyyy", the month for "mm", and the day for "dd".
		
		For times, add the class "time-hh:mm" to any <th> (or <td>)
		cells within <tbody> that are times. Naturally, the actual time
		would go in there, rather than "hh" or "mm" -- but be sure to add that 
		in 24 hour time.
		
		If you have a combination date/time, that can be denoted with "date-time...."
		and the time appended to it, separated by a "T", such as this:
		
		<th class="date-time-2006-10-16T10:39">...</th>
			 
	12. If you'd like to define a sorting tiebreaker (a column by which
		to sort in the event of a tie when sorting by a given column), add 
		the class "sorting-tiebreaker" to one of the cells in the <thead>. Or,
		if you don't want to define that, that's ok.
		
	13. Lastly, you may want to apply some CSS to highlight the sorted-by
		table header cell. Some code such as this might be of use:
		
		table.data-table thead tr th.sorted-by-this-column
		{
			background-position: 95% 50%;
			background-repeat: no-repeat;
		}

			table.data-table.ascending thead tr th.sorted-by-this-column
			{
				background-image: url(../media/icons/sortable_ascending.gif);
			}

			table.data-table.descending thead tr th.sorted-by-this-column
			{
				background-image: url(../media/icons/sortable_descending.gif);
			}
*/


var TableSorting = {

	/* This text is added to cells when the user sorts by that cell */
	SORTED_BY_TEXT : 'Sorted by',

	/* This classname can be applied to a th/td
		inside to indicate that this cell should
		act as the tiebreaker in the event
		that sorting by another column ends up
		in a tie. */
	TIEBREAKER_CELL_CLASSNAME : 'sorting-tiebreaker',
	SORTED_BY_THIS_COLUMN_CLASSNAME : 'sorted-by-this-column',

	FIRST_IN_GROUP_CLASSNAME : 'first-in-group',
	LAST_IN_GROUP_CLASSNAME : 'last-in-group',

	/* If this is true, the function applies
		'first-in-group' / 'last-in-group'
		classes to the rows after they've been sorted */
	SHOULD_APPLY_GROUPING_CLASSES : true,

	ASCENDING_SORT_CLASSNAME : 'ascending',
	DESCENDING_SORT_CLASSNAME : 'descending',

	/* This keeps track of whether a sort is currently running,
		in order to avoid race conditions */
	_isCurrentlyRunning : false,

	/* This is used internally by the sorting
		functions -- this is how they know
		which cell in a given row to sort by */
	_cellIndex : 0,

	/* This is used internally by the sorting
		functions to break a tie when two
		cells in a given column are "equal". This
		is used to then compare a second column
		to figure out which one wins. */
	_tiebreakerCellIndex : null,

	// This is used internally to cache the
	// tie breaker sorting function (so
	// that it isn't derived for every tie)
	_tiebreakerSortingFunction : null,

	// Initialize
	setOnloadEventHandler : function()
	{
		var _this = this; // Closure
		this.addEvent(window, "load", function() {
			_this._initialize();
		});
	},

	// This runs once the page has loaded
	_initialize : function()
	{
		// First, test for applicable JavaScript support
		if (!document.getElementsByTagName) return;

		// Find applicable tables
		var sortableTables = document.getElementsByTagName('table');

		for (var i=0, len=sortableTables.length; i < len; i++)
		{
			var currentTable = sortableTables[i];

			// Hook up event handlers as needed
			if (Element.hasClassName(currentTable, 'sortable'))
			{
				TableSorting.applyEventHandlers(currentTable)
		   }
		}

	},

	/* This applies event handlers to the cells within a table's thead
		element which, in turn, trigger table sorting when clicked upon */
	applyEventHandlers : function(tableElement)
	{
		tableElement = $(tableElement);

		var theadCells = $A(tableElement.tHead.rows).last().cells;

		// If there're no thead cells, exit
		if (!theadCells) return;

		for (var i=0, theadLength=theadCells.length; i < theadLength; i++)
		{
			var currentCell = theadCells[i];

			// Applly the event handler to each table header element
			currentCell.onclick = function()
			{
				TableSorting.sortTable(this);
				return false;
			}

		} // end of cell for-loop

	},

	/* This returns true if a given className is date-related
		or time-related. It's mostly used by sortTable() */
	_checkIfDateOrTimeClass : function(className)
	{
		if (className.match(/date|time/i))
		{
			return true;
		}
		else
		{
			return false;
		}
	},

	/* This takes an element as a parameter and
		returns the combined result of all its text nodes */
	getTextNodesAsString : function(element)
	{
		if (element.innerText != undefined)
		{
			return element.innerText; // not required, but faster for IE
		}

		element = $(element);

		var textNodesStringArray = []; // initialize

		// If it has no child nodes, exit
		if (!element.childNodes) return;

		// Loop through each of the element's children and combine
		// all its text nodes into a single string
		for (var i=0, len=element.childNodes.length; i < len; i++)
		{
			var currentChildNode = element.childNodes[i];

			// 3 = Text Node
			if (currentChildNode.nodeType == 3)
			{
				textNodesStringArray.push(currentChildNode.nodeValue);
			}
		}

		return textNodesStringArray.join('');
	},

	/* Given a table header element (or any of its children),
		this function derives the cell index of the tiebreaker cell
		(as defined by a th/td element in the thead having the class "tiebreaker"
	*/
	setTiebreakerCellIndex : function(tableElement)
	{
		tableElement = $(tableElement);

		tableElement = tableElement.walkUpUntil('table');

		// If no such element was found, exit
		if (tableElement == null) return;

		// Grab all rows in the thead
		theadRows = tableElement.tHead.rows;

		// Then loop through those rows and its children to find the tiebreaker

		for (var i=0, rowLength = theadRows.length; i < rowLength; i++)
		{
			var currentRow = theadRows[i];

			for (var j=0, cellLength = currentRow.cells.length; j < cellLength; j++)
			{
				var currentCell = currentRow.cells[j];

				// If the current cell has the TIEBREAKER_CELL_CLASSNAME class,
				// set the tieBreakerCellIndex to that value
				if (Element.hasClassName(currentCell, TableSorting.TIEBREAKER_CELL_CLASSNAME))
				{
					TableSorting._tiebreakerCellIndex = j;
					return TableSorting._tiebreakerCellIndex;
				}

			} // end of cell-loop

		} // end of row for-loop

		// If it got this far, then there must not be a tiebreaker cell
		// defined for the table. So, set the tieBreakerCellIndex to null
		// and return that as well.
		TableSorting._tiebreakerCellIndex = null;
		return TableSorting._tiebreakerCellIndex;
	},

	/* This derives the sorting-type for a given table cell.
		For instance, if the cell contains currency, this
		returns TableSorting.sortByCurrency();
	*/
	getSortingFunction : function(tableCellElement)
	{
		tableCellElement = $(tableCellElement);

		// By default, sort alphabetically...
		var sortingFunction = TableSorting.sortAlphabetically;

		// But, also test for other data types

		if (this._checkIfDateOrTimeClass(tableCellElement.className)) // Date/time test
		{
			return TableSorting.sortByDateOrTime;
		}
		else if (tableCellElement.hasClassName('currency')) // Currency test
		{
			return TableSorting.sortByCurrency;
		}
		else
		{
			var tableCellText = TableSorting.getTextNodesAsString(tableCellElement);

			if (tableCellText.match(/^[\d\.]+$/)) // Numbers test
			{
				return TableSorting.sortNumerically;
			}
		}

		return sortingFunction;
	},

	/* This applies "first-in-group" / "last-in-group" classes to the table rows,
		as needed */
	applyGroupingClasses : function(tbodyRows, isAscendingSort)
	{
		// If there're rows in the tbody, go through some initialization bits
		if (tbodyRows)
		{
			var previousRow = null; // initialize

			var sortingFunction = TableSorting.getSortingFunction(tbodyRows.first().cells[TableSorting._cellIndex]);
		}
		else
		{
			// If there're no rows, exit
			return;
		}

		for (i=0, len = tbodyRows.length; i < len; i++)
		{
			if (isAscendingSort)
			{
				var currentRow = tbodyRows[i];
			}
			else
			{
				// For descending sort, grab from the end an go backwards
				// (and subtract 1 since arrays are zero-based, while
				// arrayname.length is one-based)
				var currentRow = tbodyRows[len - 1 - i];
			}

			// If the previous row exists and if the previous row is different from this one,
			// set LAST_IN_GROUP_CLASSNAME on the previous row
			if (previousRow)
			{
				var isAlternateSortMode = true;
				var shouldIgnoreTies = true;
				var comparisonResult = sortingFunction(previousRow, currentRow, isAlternateSortMode, TableSorting._cellIndex, shouldIgnoreTies);

				// If they're identical...
				if (comparisonResult == 0)
				{
					// The previous row shouldn't have LAST_IN_GROUP_CLASSNAME
					Element.removeClassName(previousRow, TableSorting.LAST_IN_GROUP_CLASSNAME);

					// .. and the current row shouldn't have FIRST_IN_GROUP_CLASSNAME either
					Element.removeClassName(currentRow, TableSorting.FIRST_IN_GROUP_CLASSNAME);
				}
				else // If they aren't identical...
				{
					// The previous row gets LAST_IN_GROUP_CLASSNAME
					Element.addClassName(previousRow, TableSorting.LAST_IN_GROUP_CLASSNAME);

					// And the current row gets FIRST_IN_GROUP_CLASSNAME
					Element.addClassName(currentRow, TableSorting.FIRST_IN_GROUP_CLASSNAME);
				}

			}
			else // If there's no previous row...
			{
				Element.addClassName(currentRow, TableSorting.FIRST_IN_GROUP_CLASSNAME);
			}

			// Before going through the loop again, set the previousRow to the currentRow
			previousRow = currentRow;

		} // end of for-loop

		// Add "last-in-group" to the last guy
		if (currentRow)
		{
			Element.addClassName(currentRow, TableSorting.LAST_IN_GROUP_CLASSNAME);
		}

	},

	/* Given a th element (from a table's thead) as a parameter,
		this sorts a given table by the items in that column */
	sortTable : function(tableHeaderElement)
	{
		// Make sure a sort isn't currently running
		if (TableSorting._isCurrentlyRunning) return;

		TableSorting._isCurrentlyRunning = true;

		tableHeaderElement = $(tableHeaderElement);

		// Walk up the tree until either a th or td is found
		tableHeaderElement = $(tableHeaderElement.walkUpUntil(['th', 'td']));

		// If no such element was found, exit
		if (tableHeaderElement == null) return;

		// Next up, calculate the cellIndex
		// (the index of this cell in its row)

		var trElement = tableHeaderElement.parentNode;


		// Loop through all the children of the table header element's parent
		// row (which is to say, all of its siblings) and find the one that matches
		for (var i=0, len=trElement.cells.length; i < len; i++)
		{
			var currentCell = trElement.cells[i];

			if (currentCell == tableHeaderElement)
			{
				TableSorting._cellIndex = i;
			}

			// While we're here, remove the SORTED_BY_THIS_COLUMN_CLASSNAME class from the current cell
			Element.removeClassName(currentCell, TableSorting.SORTED_BY_THIS_COLUMN_CLASSNAME)

		} // end of for-loop


		// Reset this value, but don't re-derive it just yet
		// as there may be no need to go through that effort
		TableSorting._tiebreakerCellIndex = null;


		// Next grab all the rows in the tbody
		var tableElement = tableHeaderElement.walkUpUntil('table');

		var tbodyElements = $A(tableElement.tBodies);

		// Then loop through all the tbody elements and sort them
		for (var i=0, len=tbodyElements.length; i < len; i++)
		{
			var currentTbodyElement = tbodyElements[i];

			var tbodyRows = currentTbodyElement.rows;

			/* If there's one row or fewer, skip over this loop iteration
				- With zero rows, the table is broken
				- With one row, the table doesn't need to be sorted <g>
			*/
			if (tbodyRows.length <= 1) continue;

			// Work out a type for the column
			var exampleCell = $A(tbodyRows).first().cells[TableSorting._cellIndex];

			var sortingFunction = TableSorting.getSortingFunction(exampleCell);


			// Ready, set, sort!

			tbodyRows = $A(tbodyRows);
			tbodyRows.sort(sortingFunction);


			/* Figure out if the rows need to be put back in ascending or
				descending order */

			var isAscendingSort = true; // default

			// Find the header-classname of the clicked header cell
			var headerNameClassName = $A( tableHeaderElement.className.match(/[a-zA-Z0-9\-]+header/i) ).first();
			headerNameClassName = headerNameClassName.replace(/-header/i,''); // remove the "-header" bit

			var newTableClassName = "sorted-by-" + headerNameClassName;

			// if the table already has that classname, then we need to go for flip
			// the sort-type (either from ascending -> descending or descending -> ascending)
			if (Element.hasClassName(tableElement, newTableClassName))
			{
				if (Element.hasClassName(tableElement, TableSorting.ASCENDING_SORT_CLASSNAME))
				{
					isAscendingSort = false;

					Element.removeClassName(tableElement, TableSorting.ASCENDING_SORT_CLASSNAME);
					Element.addClassName(tableElement, TableSorting.DESCENDING_SORT_CLASSNAME);
				}
				else if (Element.hasClassName(tableElement, TableSorting.DESCENDING_SORT_CLASSNAME))
				{
					Element.removeClassName(tableElement, TableSorting.DESCENDING_SORT_CLASSNAME);
					Element.addClassName(tableElement, TableSorting.ASCENDING_SORT_CLASSNAME);
				}
			}
			else // If the table doesn't already have "sorted-by-###" that matches the clicked table header cell...
			{
				// Remove any "sorted-by" classnames from the table
				tableElement.className = tableElement.className.replace(/sorted-by-[a-zA-Z0-9\-]+/i,'');

				// And, add the new table classname
				Element.addClassName(tableElement, newTableClassName);

				// remove the "descending" classname, just in case
				Element.removeClassName(tableElement, TableSorting.DESCENDING_SORT_CLASSNAME);

				// make sure the table has the "ascending" classname
				Element.addClassName(tableElement, TableSorting.ASCENDING_SORT_CLASSNAME);
			}

			/* Then reapply the rows to the table so that the browser
				recognizes them as being changed. */

			if (isAscendingSort)
			{
				for (i=0, len=tbodyRows.length; i < len; i++)
				{
					var currentRow = tbodyRows[i];
					currentRow.parentNode.appendChild(currentRow);
				}
			}
			else
			{
				for (i=0, len=tbodyRows.length; i < len; i++)
				{
					var currentRow = tbodyRows[i];

					currentRow.parentNode.insertBefore(currentRow, currentRow.parentNode.firstChild);
				}
			}

			/* Add "sorted-by-this-column" the current header */
			tableHeaderElement.addClassName(TableSorting.SORTED_BY_THIS_COLUMN_CLASSNAME);

			/* Add/remove the sort-accessibility-text bits (via a span) */

				// First, remove any existing sort-accessibility-text elements */
				var theadSpanElements = $A( tableElement.tHead.getElementsByTagName('span') );

				for (var i=0, len=theadSpanElements.length; i < len; i++)
				{
				 	var currentSpanElement = theadSpanElements[i];

				 	if ((Element.hasClassName(currentSpanElement, 'accessibility')) &&
				 		(Element.hasClassName(currentSpanElement, 'sorted-by')))
				 	{
					 	Element.remove(currentSpanElement);
					 	currentSpanElement = null;
				 	}

				}

				// Then create the new span and add it to the beginning of the clicked cell
				var newSpanElement = document.createElement('span');
				Element.addClassName(newSpanElement, 'accessibility');
				Element.addClassName(newSpanElement, 'sorted-by');

			   	newSpanElement.appendChild(document.createTextNode(TableSorting.SORTED_BY_TEXT));

			   	// Insert the new span element as the new first child to the table header element
			   	tableHeaderElement.insertBefore(newSpanElement, tableHeaderElement.firstChild);

			// Apply "first-in-group" / "last-in-group" to the table rows
			if (TableSorting.SHOULD_APPLY_GROUPING_CLASSES)
			{
				TableSorting.applyGroupingClasses(tbodyRows, isAscendingSort);
			}

			/* If the offspring class-adjustment function exists, run it */
			if ( typeof offspring != "undefined" )
			{
				// The "true" value for the second parameter forces
				// the function to remove old classes before applying the new ones
				offspring.traverseChildren(currentTbodyElement, true);
			}

			// Reset these values
			TableSorting._tiebreakerCellIndex = null;
			TableSorting._tiebreakerSortingFunction = null;

		} // end of for-loop

		// Turn off the race-condition prevention boolean
		TableSorting._isCurrentlyRunning = false;

		return false; // Prevent the link from firing
	},

	/* This accepts two rows and sorts them by the column
		that's defined as the "tiebreaker" */
	sortByTiebreaker : function(a,b)
	{
		// First, make sure the tiebreakerCellIndex is defined
		if (TableSorting._tiebreakerCellIndex == null)
		{
			// (TableSorting.setTiebreakerCellIndex needs one
			// of the elements from the given table to derive
			// the appropriate tiebreakerCellIndex)
			TableSorting.setTiebreakerCellIndex(a);

			// Then, if the tiebreakerCellIndex still isn't defined,
			// then there must not be one for this table, so just return 0
			if (TableSorting._tiebreakerCellIndex == null)
			{
				return 0;
			}
		}

		// Then, if the TieBreakerCellIndex equals the current
		// cell index (in other words, if it's not possible to resolve
		// this tie), just return 0
		if (TableSorting._tiebreakerCellIndex == TableSorting._cellIndex)
		{
			return 0;
		}

		// At this point, we need to sort them by the tiebreaker cell

		if (TableSorting._tiebreakerSortingFunction == null)
		{
			TableSorting._tiebreakerSortingFunction = TableSorting.getSortingFunction(a.cells[TableSorting._tiebreakerCellIndex]);
		}

		/*
			In "alternate sort mode", the function blindly relies upon
			arbitraryCellIndex as its cell index to sort by, rather than making
			use of TableSorting._cellIndex.
		*/
		var isAlternateSortMode = true;

		var shouldIgnoreTies = true;
		return (TableSorting._tiebreakerSortingFunction(a,b, isAlternateSortMode, TableSorting._tiebreakerCellIndex, shouldIgnoreTies));
	},

	/* This accepts two rows and sorts alphabetically them by a given
		cell (as specified in TableSorting.cellIndex)

		== only the first two parameters are required (the others are optional) ==

		In "alternate sort mode", the function blindly relies upon
		arbitraryCellIndex as its cell index to sort by, rather than making
		use of TableSorting._cellIndex. As you might guess, "alternate
		sort mode" is typically used just for resolving tiebreakers.

		If "shouldIgnoreTies" is specified, the function doesn't resolve
		tiebreakers if that scenario occurs.
	*/
	sortAlphabetically : function(a, b, isAlternateSortMode, arbitraryCellIndex, shouldIgnoreTies)
	{
		if (isAlternateSortMode)
		{
			var cellIndexToSortBy = arbitraryCellIndex;
		}
		else
		{
			var cellIndexToSortBy = TableSorting._cellIndex;
		}

		var firstString = TableSorting.getTextNodesAsString(a.cells[cellIndexToSortBy]).toLowerCase();
		var secondString = TableSorting.getTextNodesAsString(b.cells[cellIndexToSortBy]).toLowerCase();

		if (firstString == secondString)
		{
			if (shouldIgnoreTies)
			{
				return 0;
			}
			else
			{
				return TableSorting.sortByTiebreaker(a,b);
			}
		}

		if (firstString < secondString) return -1;
		return 1;
	},

	/* This accepts two rows and sorts by date or time for a given
		cell (as specified in TableSorting.cellIndex)

		== only the first two parameters are required (the others are optional) ==

		In "alternate sort mode", the function blindly relies upon
		arbitraryCellIndex as its cell index to sort by, rather than making
		use of TableSorting._cellIndex. As you might guess, "alternate
		sort mode" is typically used just for resolving tiebreakers.

		If "shouldIgnoreTies" is specified, the function doesn't resolve
		tiebreakers if that scenario occurs.
	*/
	sortByDateOrTime: function(a, b, isAlternateSortMode, arbitraryCellIndex, shouldIgnoreTies)
	{
		if (isAlternateSortMode)
		{
			var cellIndexToSortBy = arbitraryCellIndex;
		}
		else
		{
			var cellIndexToSortBy = TableSorting._cellIndex;
		}

		/* Here's what this sequence does:
			- Grabs the classnames for the specified cell of the first row
			- Goes through the classnames and finds the one that's date related (such as "date-2006-09-24")
			- For that class, remove all the bits that aren't numbers, leaving behind the date
		*/
		var firstCellDateTimeClassName = $A( Element.classNames(a.cells[cellIndexToSortBy]) ).find(TableSorting._checkIfDateOrTimeClass);
		if (!firstCellDateTimeClassName) throw "Tried to compare cells by date or time but one of the cells has neither a date nor time class. Classes found: " + Element.classNames(a.cells[cellIndexToSortBy]);

		var firstISODateTimeString = firstCellDateTimeClassName.replace(/[^0-9]/g,'');


		var secondCellDateTimeClassName = $A( Element.classNames(b.cells[cellIndexToSortBy]) ).find(TableSorting._checkIfDateOrTimeClass);
		if (!secondCellDateTimeClassName) throw "Tried to compare cells by date or time but one of the cells has neither a date nor time class. Classes found: " + Element.classNames(b.cells[cellIndexToSortBy]);

		var secondISODateTimeString = secondCellDateTimeClassName.replace(/[^0-9]/g,'');

		/* This part compares the two dates as strings -- this is intentional :).
			For instance, "200609" is greater than "20060415", date-wise,
			even though 200,609 is smaller than 20,060,415 numerically.
		*/
		if (firstISODateTimeString == secondISODateTimeString)
		{
			if (shouldIgnoreTies)
			{
				return 0;
			}
			else
			{
				return TableSorting.sortByTiebreaker(a,b);
			}
		}
		if (firstISODateTimeString < secondISODateTimeString) return -1;
		return 1;
	},

	/* This accepts two rows and sorts by currency for a given
		cell (as specified in TableSorting.cellIndex)

		== only the first two parameters are required (the others are optional) ==

		In "alternate sort mode", the function blindly relies upon
		arbitraryCellIndex as its cell index to sort by, rather than making
		use of TableSorting._cellIndex. As you might guess, "alternate
		sort mode" is typically used just for resolving tiebreakers.

		If "shouldIgnoreTies" is specified, the function doesn't resolve
		tiebreakers if that scenario occurs.
	*/
	sortByCurrency : function(a, b, isAlternateSortMode, arbitraryCellIndex, shouldIgnoreTies)
	{
		if (isAlternateSortMode)
		{
			var cellIndexToSortBy = arbitraryCellIndex;
		}
		else
		{
			var cellIndexToSortBy = TableSorting._cellIndex;
		}

		/* Here's what this sequence does:
			- Grabs the specified cell within the row
			- Throws away all non-text-node children nodes
			- Removes any non-numeric characters
			- Converts that to a number
		*/
		var firstCellString = TableSorting.getTextNodesAsString(a.cells[cellIndexToSortBy]);
		var firstCellValue = parseFloat( firstCellString.replace(/[^0-9.\-]/g,'') );

		// For safety, make sure it's a number too
		if (isNaN(firstCellValue)) firstCellValue = 0;

		var secondCellString = TableSorting.getTextNodesAsString(b.cells[cellIndexToSortBy]);
		var secondCellValue = parseFloat( secondCellString.replace(/[^0-9.\-]/g,'') );

		// For safety, make sure it's a number too
		if (isNaN(secondCellValue)) secondCellValue = 0;

		var currencyDifference = (firstCellValue - secondCellValue);

		if ((currencyDifference == 0) && (!shouldIgnoreTies))
		{
			return TableSorting.sortByTiebreaker(a,b);
		}
		else return currencyDifference;
	},

	/* This accepts two rows and sorts them numerically for a given
		cell (as specified in TableSorting.cellIndex)

		== only the first two parameters are required (the others are optional) ==

		This is pretty similar to sortByCurrency, except that it doesn't
		filter the cell contents to remove non-numeric characters before
		it goes about sorting.

		In "alternate sort mode", the function blindly relies upon
		arbitraryCellIndex as its cell index to sort by, rather than making
		use of TableSorting._cellIndex. As you might guess, "alternate
		sort mode" is typically used just for resolving tiebreakers.

		If "shouldIgnoreTies" is specified, the function doesn't resolve
		tiebreakers if that scenario occurs.
	*/
	sortNumerically : function(a, b, isAlternateSortMode, arbitraryCellIndex, shouldIgnoreTies)
	{
		if (isAlternateSortMode)
		{
			var cellIndexToSortBy = arbitraryCellIndex;
		}
		else
		{
			var cellIndexToSortBy = TableSorting._cellIndex;
		}

		var firstCellValue = parseFloat( TableSorting.getTextNodesAsString(a.cells[cellIndexToSortBy]) );

		// For safety, make sure it's a number too
		if (isNaN(firstCellValue)) firstCellValue = 0;

		var secondCellValue = parseFloat( TableSorting.getTextNodesAsString(b.cells[cellIndexToSortBy]) );

		// For safety, make sure it's a number too
		if (isNaN(secondCellValue)) secondCellValue = 0;


		var difference = firstCellValue - secondCellValue;

		if ((difference == 0) && (shouldIgnoreTies))
		{
			return TableSorting.sortByTiebreaker(a,b);
		}
		else return difference;
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
}

// Rig up the onload event handler
TableSorting.setOnloadEventHandler();