/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */







$(document).ready(function (){   
   $("#create").hide(); 
   var url = "select";
   document.theform.action = url; 
    
 
   var urls = "test";
   document.theform.action = urls; 
   
 $("#check").on('click', function (){
 var status = document.getElementById("check");
  var newfield = '<input id= "pass2" type="password" class="form-control" placeholder=" Re-Enter Password" required>';   
 if (status.checked){
     $("#pass").after(newfield);
     $("#submit").hide();
     $("#create").show();
     $("h2").text("REGISTER"); 
         
    }
    else{
        $("#pass2").remove();
        $("#create").hide();
        $("#submit").show();
        $("h2").text("PLEASE LOG IN");
        
    }});  
    
 $("#submit").on('click',function (){
       
        document.theform.method = "POST";
        document.theform.action = url;  
       document.getElementById('theform').submit();   

}); // handles form validation and sign form
   $("#create").on('click',function (){ 
      document.theform.method = "POST"; 
     var pass = document.getElementById("pass").value;
     var pass2=  document.getElementById("pass2").value;
      var name=  document.getElementById("pass2").value;
       if( name && pass && pass !== pass2)
      {
          alert("Passwords do not Match!!");
          var pass = document.getElementById("pass").value = null;
          document.getElementById("pass2").value = null;
           return false;          
      }
      else {       
        document.theform.action = urls;  
        document.getElementById('theform').submit();          
      }
}); // handles form validation and Create User form  form

});

