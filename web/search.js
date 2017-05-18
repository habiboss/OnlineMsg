$(document).ready(function() {  

       function getXMLHttpRequest() 
{
    if (window.XMLHttpRequest) {
        return new window.XMLHttpRequest;
    }
    else {
        try {
            return new ActiveXObject("MSXML2.XMLHTTP.3.0");
        }
        catch(ex) {
            return null;
        }
    }
} 

    
    $("#search").on('click', function() {
                     var search = document.getElementById("search").value;
var url = "/message/webresources/searchServlet";
var ajaxRequest = getXMLHttpRequest();
var data = {"search": search
           }; 

 if (ajaxRequest) {  
    ajaxRequest.onreadystatechange = ajaxResponse;  
   ajaxRequest.open("GET", url);
   ajaxRequest.send(JSON.stringify(data));  
   
 }
 
 
function ajaxResponse()   
{
   if (ajaxRequest.readyState != 4) {  return;  }
     else {
     if (ajaxRequest.status == 200) 
          {   $("#maintable").replaceWith(ajaxRequest.responseText);
        }
     else {
       alert("Request failed: " + ajaxRequest.statusText);
          }
     }
}
                  });              
                  });