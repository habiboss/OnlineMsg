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
$(document).ready(function() {
    
 //////////////////////////////////////////////show create message form          
    $("#createnewmessage").hide();
    $("#maintable").hide();
     $("#search").hide();
     $("#update").hide(); 
    var privacy = "public";
    $('#myAnchor').text($('#welcome').val());
    var url = "index.html";
/////////////////////////////////////////////Validate Session  
var named = $('#welcome').val();
var names =  $('#myAnchor').text();
//if (named == '') {
//alert("Your Session has expired: " + " Please Log in First");
//$(location).attr('href',url);
//}
//if (named == null) {
//alert("Your Session has expired: " + " Please Log in First");
//$(location).attr('href',url);
//}
    
/////////////////////////////////////////view private message
 $("#viewprivatemessage").on('click', function() {
         
          $("#maintable").show();
              $("#createnewmessage").hide();
              $("#search").hide();
              $("#update").hide(); 
   var url = "/message/webresources/service.login";
      var name = $("#welcome").val();
      var ajaxRequest = getXMLHttpRequest();  
      if (ajaxRequest) {  
      var myRand = parseInt(Math.random()*999999999999999);
      var posturl = url+"/"+name+"/"+myRand.toString();
      ajaxRequest.onreadystatechange = ajaxResponse;  
      ajaxRequest.open("GET",posturl);
      ajaxRequest.send(null);
   }         
   function ajaxResponse()   
{
   if (ajaxRequest.readyState != 4) {  return;  }
     else {
     if (ajaxRequest.status == 200) 
          {   $("#maintable").replaceWith(ajaxRequest.responseText);
          $("#msg").val('');
        }
     else {
       alert("Request failed: " + ajaxRequest.statusText);
          }
     }
}               
 });
 /////////////////////////////////////
// $("#logout").on('click',function (){
//       var url = "logout";
//        document.theform.method = "POST";
//        document.theform.action = url;  
//       document.getElementById('theform').submit();  
//       
// });
 
 //////////View public message
 $("#viewpublicmessage").on('click', function() {
      $("#maintable").show();
     $("#createnewmessage").hide();
     $("#search").hide();
     $("#update").hide(); 
var url = "/message/webresources/service.login";
      var ajaxRequest = getXMLHttpRequest();  
      if (ajaxRequest) {  
      var myRand = parseInt(Math.random()*999999999999999);
      var posturl = url+"/public/"+myRand.toString();
      ajaxRequest.onreadystatechange = ajaxResponse;  
      ajaxRequest.open("GET",posturl);
      ajaxRequest.send(null);
     
   }
   
       function ajaxResponse()   
{
   if (ajaxRequest.readyState != 4) {  return;  }
     else {
     if (ajaxRequest.status == 200) 
          {   $("#maintable").replaceWith(ajaxRequest.responseText);
          $("#msg").val('');
        }
     else {
       alert("Request failed: " + ajaxRequest.statusText);
          }
     }
}              
});
 
 //////////check checkbox
   $("#check").on('click', function(){
     var status = document.getElementById("check");
     if(status.checked){
         privacy = "private";      
     }
     else{
         privacy = "public";
     }
     
 });
 ///////////////////////
 $("#search_message").on('click', function(){
    $("#search").show(); 
       $("#createnewmessage").hide();
    $("#maintable").hide();
    $("#update").hide(); 
    
 });
 ////////////////////////////////
 $("#sh_select").on('click', function(){
      var url = "/message/webresources/service.login";
      var ajaxRequest = getXMLHttpRequest();  
      //var name = $("#welcome").val();
     // var data = {"name": name
      //     }; 
      if (ajaxRequest) {  
      var posturl = url;
      ajaxRequest.onreadystatechange = ajaxResponse;  
      ajaxRequest.open('GET',posturl);
      //ajaxRequest.send(JSON.stringify(data));  
      ajaxRequest.send();
     
   }


// if (ajaxRequest) {  
//    ajaxRequest.onreadystatechange = ajaxResponse;  
//   ajaxRequest.open("GET", url);
//   ajaxRequest.send(JSON.stringify(data));  
//   
// }
   
       function ajaxResponse()   
{
   if (ajaxRequest.readyState != 4) {  return;  }
     else {
     if (ajaxRequest.status == 200) 
          {   $("#maintable").replaceWith(ajaxRequest.responseText);
          $("#msg").val('');
        }
     else {
       alert("Request failed: " + ajaxRequest.statusText);
          }
     }
}   
 });
 /////////////////////////////////////
 $('#update_message').on('click', function(){
     $("#update").show(); 
     $("#maintable").hide();
     $("#createnewmessage").hide();
     $("#search").hide();
     
     var url = "/message/webresources/service.login";
     var ajaxRequest = getXMLHttpRequest();  
     if (ajaxRequest) {  
     var posturl = url;
     ajaxRequest.onreadystatechange = ajaxResponse;  
     ajaxRequest.open('GET',posturl);
     ajaxRequest.send(null);
     
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
 
 //////////////////////////////////
  //////////create message  
 $("#createmessage").on('click', function() {
         
          $("#createnewmessage").show();
           $("#maintable").hide();
            $("#search").hide();
            $("#update").hide(); 
          $("#msg").focus();
     
 });

 ////saving message
$("#savemessage").on('click', function() {
var url = "/message/webresources/service.login";
var name = $("#welcome").val();
var message = $("#msg").val(); 

if (message == '') {
    alert ("Please type in a message before saving");   
}
var ajaxRequest = getXMLHttpRequest();
var data = {"name": name,
            "message": message,
            "privacy": privacy
           }; 

 if (ajaxRequest) {  
    ajaxRequest.onreadystatechange = ajaxResponse;  
   ajaxRequest.open("POST", url);
   ajaxRequest.send(JSON.stringify(data));  
   
 }
 
 
function ajaxResponse()   
{
   if (ajaxRequest.readyState != 4) {  return;  }
     else {
     if (ajaxRequest.status == 200) 
          {   $("#maintable").replaceWith(ajaxRequest.responseText);
          $("#msg").val('');
          alert ("Message Saved");
        //  location.reload();
        }
     else {
       alert("Request failed: " + ajaxRequest.statusText);
          }
     }
}



});  

$("#logout").on('click', function() {
    
  var url = "/message/logout";

  $.ajax({
  type: "POST",
  url: url,
  context: document.body
}).done(function() {
  $( this ).addClass( "done" );
});
            
            
});


});


function processtable(){
    var url = "/message/webresources/service.login";
 
    
    $('.btn-default').on('click', function(){
           var privacy = "public";
        $(this).closest('.datarow').find('.privacycell').text(privacy);
           var currentcell = $(this).closest('.datarow').find('.messagecel').text();
          
         
          var data ={ "message": currentcell,
                        "privacy": privacy
                        } ;
                 
  $.ajax({
   url: url,
    type: 'PUT',
    data: JSON.stringify(data),
    success: function(result) {
        
        
    }
   
   
});
           
  });
       
//         $('.btn-success').on('click', function(){
//                var privacy = "private";
//            $(this).closest('.datarow').find('.privacycell').text(privacy);  
//           var currentcell = $(this).closest('.datarow').find('.messagecel').text();
//          
//            
//             var data ={ "message": currentcell,
//                        "privacy": privacy
//                        }     ;
//  $.ajax({
//    url: url,
//    type: 'PUT',
//    data: JSON.stringify(data),
//    success: function(result) {
//         alert(result);   
//    }
//   
//   
//});
//          
// });
        
        
        
        
        
    $('.btn-warning').on('click', function(){
        
         
    var currentcell = $(this).closest('.datarow').find('.messagecel').text();
    
    var data = {"message": currentcell};
  
    var r=confirm("You are about to Delete: \n  "+currentcell+" ");
if (r==true)
  {
    $.ajax({
    url: url,
    type: 'DELETE',
    data: JSON.stringify(data),
    success: function(result) {
       
       alert(result);
    }
});
        $(this).closest('.datarow').remove();
        //r.close();
       
  
  }
else
  { 
  r.close();
  } 
    
    
   
      
      
    });
   
    
    }