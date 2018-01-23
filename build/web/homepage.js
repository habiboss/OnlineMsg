///////////////////////////////////////////


/////////////////////////////////////////
function getXMLHttpRequest()
{
  if (window.XMLHttpRequest) {
    return new window.XMLHttpRequest;
  } else {
    try {
      return new ActiveXObject("MSXML2.XMLHTTP.3.0");
    } catch (ex) {
      return null;
    }
  }
}


$(document).ready(function () {

  //////////////////////////////////////////////show create message form
  $("#createnewmessage").hide();
  $("#maintable").hide();
  $("#search").hide();
  $("#update").hide();
  $("#update_msg").hide();
  var privacy = "public";
  $('#myAnchor').text($('#welcome').val());
  var url = "index.html";
  /////////////////////////////////////////////Validate Session
  var session = $('#welcome').val();
  var names = $('#myAnchor').text();
  var x = null;
  //if (names == '') {
  //alert("Your Session has expired: " + " Please Log in First");
  //$(location).attr('href',url);
  //}
  var session = $('#welcome').val();
  //if (session == "null") {
  //alert("Your Session is invalid: " + " Please Log in First");
  //$(location).attr('href',url);
  //}
  //////////////////////////////////////////////
  var ajaxRequest = getXMLHttpRequest();
  function ajaxResponse()
  {
    if (ajaxRequest.readyState != 4) {
      return;
    } else {
      if (ajaxRequest.status == 200)
      {
        $("#maintable").replaceWith(ajaxRequest.responseText);
      } else {
        alert("Request failed: " + ajaxRequest.statusText);
      }
    }
  }
  /////////////////////////////////////////view private message
  $("#viewprivatemessage").on('click', function () {

    $("#maintable").show();
    $("#createnewmessage").hide();
    $("#search").hide();
    $("#update").hide();
    var url = "/message/webresources/service.login";
    var name = $("#welcome").val();
    var ajaxRequest = getXMLHttpRequest();
    if (ajaxRequest) {
      var myRand = parseInt(Math.random() * 999999999999999);
      var posturl = url + "/" + name + "/" + myRand.toString();
      ajaxRequest.onreadystatechange = ajaxResponse;
      ajaxRequest.open("GET", posturl);
      ajaxRequest.send(null);
    }
    function ajaxResponse()
    {
      if (ajaxRequest.readyState != 4) {
        return;
      } else {
        if (ajaxRequest.status == 200)
        {
          $("#maintable").replaceWith(ajaxRequest.responseText);
        } else {
          alert("Request failed: " + ajaxRequest.statusText);
        }
      }
    }
  });
  /////////////////////////////////////
  function search() {

    var users = $("#searche").val();
    var posturl = url + "/" + name + "/" + myRand.toString();
    if (users != "") {
      $("#result").html("<img alt='ajax search'");
      $.ajax({
        type: "GET",
        url: "/message/webresources/service.login/search",
        data: users,
        success: function (data) {
          $("#result").html(data);
          $("#searche").val("");

        }
      });
    }
  }

  $("#submit").on('click', function () {
    //search();
    var users = $("#searche").val();

    if (users != "") {
      $("#result").html("<img alt='ajax search'");
      //            $.ajax({
      //                type: "GET",
      //                url: "/message/webresources/service.login/search"+ "/" + users,
      //                //data: users,
      //                success: function (data) {
      //                    alert(data);
      //                    $("#aaa").html(data);
      //                    $("#searche").val("");
      //
      //                }
      //            });
      $.getJSON("/message/webresources/service.login/search"+ "/" + users, function(data){
        //alert(data);
        $.each( data, function( key, val ) {
          for (i = 0; i < val.length; i++) {
            // var selectElement = document.getElementById("userName");
            // var options = document.createElement("option");
            //options.text = val[i].UserName;
            //selectElement.add(options);
            $("#trial").val(val[i].Id);
          }
        });

      });
    }
  });

  $('#searche').keyup(function (e) {
    if (e.keyCode == 13) {
      search();
    }
  });
  //////////View public message
  $("#viewpublicmessage").on('click', function () {
    $("#maintable").show();
    $("#createnewmessage").hide();
    $("#search").hide();
    $("#update").hide();
    var url = "/message/webresources/service.login";
    var ajaxRequest = getXMLHttpRequest();
    if (ajaxRequest) {
      var myRand = parseInt(Math.random() * 999999999999999);
      var posturl = url + "/public/" + myRand.toString();
      ajaxRequest.onreadystatechange = ajaxResponse;
      ajaxRequest.open("GET", posturl);
      ajaxRequest.send(null);

    }

    function ajaxResponse()
    {
      if (ajaxRequest.readyState != 4) {
        return;
      } else {
        if (ajaxRequest.status == 200)
        {
          $("#maintable").replaceWith(ajaxRequest.responseText);
        } else {
          alert("Request failed: " + ajaxRequest.statusText);
        }
      }
    }
  });

  //////////check checkbox
  $("#check").on('click', function () {
    var status = document.getElementById("check");
    if (status.checked) {
      privacy = "private";
    } else {
      privacy = "public";
    }

  });
  ///////////////////////
  $("#search_message").on('click', function () {
    $("#search").toggle('slow');
    $("#createnewmessage").hide();
    $("#maintable").hide();
    $("#update").hide();
    $("#searche").focus();
  });

  ///////////// Get a list of all registered users
  var loginUsername = $('#myAnchor').text();
  $.getJSON("/message/webresources/service.message/getUsers/"+loginUsername+" ", function(data){
    $.each( data, function( key, val ) {
      for (i = 0; i < val.length; i++) {
        var selectElement = document.getElementById("userName");
        var options = document.createElement("option");
        options.text = val[i].UserName;
        selectElement.add(options);
      }
    });

  });

  //////////create message
  $("#createmessage").on('click', function () {
    $("#createnewmessage").toggle('slow');
    $("#maintable").hide();
    $("#search").hide();
    $("#update").hide();
    $("#msg").focus();
    $("#msg").val('');
    $("#savemessage").show();
    $("#update_msg").hide();
  });
  ///////////////////////////////////
  $("#update_msg").on('click', function () {
    var url = "/message/webresources/service.login";
    var name = $("#welcome").val();
    var message = $("#msg").val();
    var currentid = $("#currentid").val();
    if (message == '') {
      alert("Please type in a message before updating");
    }
    var ajaxRequest = getXMLHttpRequest();
    var data = {"currentid": currentid,
    "message": message,
    "privacy": privacy
  };

  if (ajaxRequest) {
    ajaxRequest.onreadystatechange = ajaxResponse;
    ajaxRequest.open("PUT", url);
    ajaxRequest.send(JSON.stringify(data));

  }


  function ajaxResponse()
  {
    if (ajaxRequest.readyState != 4) {
      return;
    } else {
      if (ajaxRequest.status == 200)
      {
        $("#msg").val('');
        alert("Message updated");
      } else {
        alert("Request failed: " + ajaxRequest.statusText);
      }
    }
  }

});

////saving message
$("#savemessage").on('click', function () {
  var url = "/message/webresources/service.login";
  var name = $("#welcome").val();
  var message = $("#msg").val();
  var toUser = $("#userName").val();
  if (message != "") {

    var ajaxRequest = getXMLHttpRequest();
    var data = {"name": name,
    "message": message,
    "privacy": privacy,
    "toUser": toUser
  };

  if (ajaxRequest) {
    ajaxRequest.onreadystatechange = ajaxResponse;
    ajaxRequest.open("POST", url);
    ajaxRequest.send(JSON.stringify(data));

    function ajaxResponse()
    {
      if (ajaxRequest.readyState != 4) {
        return;
      } else {
        if (ajaxRequest.status == 200)
        {
          $("#msg").val('');
          alert("Message Saved");
        } else {
          alert("Request failed: " + ajaxRequest.statusText);
        }
      }
    }

  }
} else {

  alert("Please type in a message before saving");
}




});

$("#logout").on('click', function () {

  var url = "/message/logout";

  $.ajax({
    type: "POST",
    url: url,
    context: document.body
  }).done(function () {
    $(this).addClass("done");
  });


});


});


function processtable() {
  var url = "/message/webresources/service.login";


  $('.btn-default').on('click', function () {
    var privacy = "public";
    $(this).closest('.datarow').find('.privacycell').text(privacy);
    var currentcell = $(this).closest('.datarow').find('.messagecel').text();
    var currentid = $(this).closest('.datarow').find('#id').text();

    var data = {"message": currentcell,
    "privacy": privacy,
    "currentid": currentid
  };

  var ajaxRequest = getXMLHttpRequest();

  if (ajaxRequest) {
    ajaxRequest.onreadystatechange = ajaxResponse;
    ajaxRequest.open("PUT", url);
    ajaxRequest.send(JSON.stringify(data));

  }


  function ajaxResponse()
  {
    if (ajaxRequest.readyState != 4) {
      return;
    } else {
      if (ajaxRequest.status == 200)
      {
      } else {
        alert("Request failed: " + ajaxRequest.statusText);
      }
    }
  }

});

$('.btn-success').on('click', function () {
  var privacy = "private";
  $(this).closest('.datarow').find('.privacycell').text(privacy);
  var currentcell = $(this).closest('.datarow').find('.messagecel').text();
  var currentid = $(this).closest('.datarow').find('#id').text();

  var data = {"message": currentcell,
  "privacy": privacy,
  "currentid": currentid
};
var ajaxRequest = getXMLHttpRequest();

if (ajaxRequest) {
  ajaxRequest.onreadystatechange = ajaxResponse;
  ajaxRequest.open("PUT", url);
  ajaxRequest.send(JSON.stringify(data));

}


function ajaxResponse()
{
  if (ajaxRequest.readyState != 4) {
    return;
  } else {
    if (ajaxRequest.status == 200)
    {
      //$("#maintable").replaceWith(ajaxRequest.responseText);
    } else {
      alert("Request failed: " + ajaxRequest.statusText);
    }
  }
}
});


$('.btn-warning').on('click', function () {


  var currentcell = $(this).closest('.datarow').find('.messagecel').text();

  var data = {"message": currentcell};

  var r = confirm("Warning!! the following Message will be Deleted!\n\n >>>>  " + currentcell + "  <<<<");
  if (r == true)
  {
    $.ajax({
      url: url,
      type: 'DELETE',
      data: JSON.stringify(data),
      success: function (result) {

        alert(result);
      }
    });
    $(this).closest('.datarow').remove();
    r.close();


  } else
  {
    r.close();
  }





});

$('.edit').on('click', function () {
  var currentcell = $(this).closest('.datarow').find('.messagecel').text();
  var currentid = $(this).closest('.datarow').find('#id').text();
  var msg = currentcell;
  $("#maintable").hide();
  $("#search").hide();
  $("#update").hide();
  $("#createnewmessage").show();
  $("#msg").val(msg);
  $("#savemessage").hide();
  $("#update_msg").show();
  $("#currentid").val(currentid);

});



}
