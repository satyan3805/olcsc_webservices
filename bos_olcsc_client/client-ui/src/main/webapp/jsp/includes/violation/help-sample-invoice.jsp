<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>Sample Invoices - Harris County Toll Road Authority</title>
  </head>

  <body>
    <div id="content-container">

      <form id="sample-invoice" action="${pageContext.request.contextPath}/violatorLogin.do" method="post" autocomplete="off">

        <div id="primary-and-secondary-content">
          
          <div id="primary-content">

            <div class="section">

              <h2>Sample Toll Violation Invoices</h2>
              <p id="unpaid-violation-image">Be sure to use the license plate and invoice number listed on your invoice.</p>

<!-- Agency Id Begins -->
Select your toll agency to view a sample invoice
<select id="agency-id" name="agencyId" onchange="changeImage(this);">
<option value="HarrisCounty">Harris County</option>
<option value="FortBend">Fort Bend</option>
</select>
<!-- Agency Id Ends -->

<div id="harris-image" style="">
<img src="${pageContext.request.contextPath}/meta/media/violations/invoice-thumbnail.gif" alt="Harris County Sample Invoice" /></div>
<div id="fortbend-image" style="display:none">
<img src="${pageContext.request.contextPath}/meta/media/violations/unpaid-fortbend-thumbnail.gif" width="350px" height="150px" alt="Fort Bend Sample Invoice" /></div>
<br />

<p><a href="#close" onclick="javascript:window.close();return false;">Close</a></p>
            </div> <!-- end of section -->

          </div> <!--end of primary-content -->

        </div> <!--end of primary-and-secondary-content -->

      </form>

    </div> <!-- end of content-container -->
  </body>

  <script type="text/javascript"> 
    function changeImage(elemt) {
      if(elemt.value == "FortBend") {
        document.getElementById("harris-image").style.display = 'none';
        document.getElementById("fortbend-image").style.display = '';
        //document.getElementById("harris-image-below").style.display = 'none';
        //document.getElementById("fortbend-image-below").style.display = '';
      } else if(elemt.value == "HarrisCounty") {
        document.getElementById("harris-image").style.display = '';
        document.getElementById("fortbend-image").style.display = 'none';
        //document.getElementById("harris-image-below").style.display = '';
        //document.getElementById("fortbend-image-below").style.display = 'none';
      } else {
        document.getElementById("harris-image").style.display = 'none';
        document.getElementById("fortbend-image").style.display = 'none';
        //document.getElementById("harris-image-below").style.display = '';
        //document.getElementById("fortbend-image-below").style.display = 'none';
      }
    }
  </script>

<!-- Created: Fri Dec 18 13:00:11 CST 2009 -->
</html>
