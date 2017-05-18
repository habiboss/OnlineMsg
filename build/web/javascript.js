$( document ).ready(function() {

 var url = "sessionExpired";

           
     var ajaxRequest = getXMLHttpRequest();  
     if (ajaxRequest) {  
     var posturl = url;
     ajaxRequest.onreadystatechange = ajaxResponse;  
     ajaxRequest.open("GET",posturl);
     ajaxRequest.send();  
     
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