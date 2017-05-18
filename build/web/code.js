$(document).ready(function(){
		

function OneClick(e){
	e.stopPropagation();
	e.preventDefault();	
}

	
$(".jaxLoader").ajaxStart(function(){
	$(this).show();
});

$(".jaxLoader").ajaxComplete(function(){
	$(this).fadeOut('slow'); //init();
});

	String.prototype.isEmpty = function(){
		var $str = $.trim(this); if($str.length > 0) return false;
		else return true;
	}
	
	$("#add_more").live('click',function(){ 
		var $html = "<input type='text' name='module[]'  class='atext' /> <br />";$($html).insertBefore($(this));
	})
	
	$("#add_more2").live('click',function(){ 
		var $html = "<input type='text' name='institute[]'  class='atext'  /> <br />";	$($html).insertBefore($(this));
	})


	$("#Add_New_Inst").click(function(){
		var $name = $('#Inst_Name').val(); if($name.isEmpty()) return false;
		var idata = {'name' : $name, 'ifunction':'AddNewInstitution'};var link = './bin/handler.php';
		$.get(link,idata,function(data){$(data).insertAfter('#inst_p_header');})
	})
	

	
	$(".remove_inst").live('click',function(){
		var idata = {'id': $(this).attr('id'),'ifunction':'RemoveInstitute'}
		var link = './bin/handler.php';var $this = $(this);
		$.get(link,idata,function(data){if(data == 1){$this.parent().parent().remove();}})
	})
	
	$(".rename_inst").live('click',function(){
		$("#Inst_Name").val($(this).parent().parent().text());
		$(".rename_inst,.remove_inst,.goCourse").hide();
		$('#cancel_rename,#Update_rename').show();
		$(this).parent().parent().addClass('hightlight_rename').siblings().each(function(){ $(this).addClass('disable'); })
		$("#Add_New_Inst").hide();
		$('#Update_rename').attr('name',$(this).attr('id'));
		
	})
	
	$("#cancel_rename").click(function(){
		window.location.reload();
	})
	
	$("#Inst_Name").keyup(function(){
		var $v = $(this).val();
		$('.hightlight_rename td:first').text($v);
	})
	
	$('#Update_rename').live('click',function(){
		var getAttr = $(this).attr('name')+ '&&' + $("#Inst_Name").val();
		var idata = {'id': getAttr,'ifunction':'UpdateInstitute'}
		var link = './bin/handler.php';var $this = $(this);
		$.get(link,idata,function(data){ //alert(data);
			Undo_remane()
		})
	})
	
	function Undo_remane(){
		$(".rename_inst,.remove_inst,.goCourse").show();
		$('#cancel_rename,#Update_rename').hide();
		$('#inst_p_header').nextAll().each(function(){ $(this).removeClass('disable').removeClass('hightlight_rename') })
		$("#Add_New_Inst").show();
		$("#Inst_Name").val("");
	}
	
	
	
	$("#btnAddCourse").click(function(){
		var $name = $('#txtCourse').val(); if($name.isEmpty()) return false;
		var $duration = $('#txtCourseDuration').val();
		var $inst_code = $(this).attr('name');
		var idata = {'name' : $name, 'duration':$duration,'inst_code':$inst_code,'ifunction':'AddNewCourse'};var link = './bin/handler.php';
		$.get(link,idata,function(data){
			if(data)
			$(data).prependTo('#select_course');
		})
	})
	
	$("#btnUpdateCourse").live('click',function(){
		var $name = $('#txtCourse').val(); if($name.isEmpty()) return false;
		var $duration = $('#txtCourseDuration').val();
		var $course_code = $(this).attr('name');
		var idata = {'name' : $name, 'duration':$duration,'course_code':$course_code ,'ifunction':'UpdateCourse'};var link = './bin/handler.php';
		$.get(link,idata,function(data){ 
			if(data == 1)
			$('#select_course').find('#' + $course_code).text($name + ' - ' + $duration);
		})	
	})
	
	$("#select_course").live('change',function(){
		var $val = new String($(this).val());
		var $split =  $val.split('&&');
		var course_code = $split[0];
		
		$('#txtCourse').val($split[1]); $('#txtCourseDuration').val($split[2]);
		$('#btnAddModule,#btnRemoveCourse,#btnUpdateCourse').attr('name',course_code);
		$('#module_label').text($split[1]);
		$('#btnRemoveCourse,#btnUpdateCourse').show();
		$('#btnRemoveModule').hide();
		var idata = {'code' : course_code, 'ifunction':'getModule'}; 
		var link = './bin/handler.php';
		$.get(link,idata,function(data){ //alert(data);
			$('#Module_list').html(data);
		})		
		
	})	
	
	$("#Module_list").live('change',function(){
		var course_code = $(this).val()
		$('#btnRemoveModule').attr('name',course_code);
		$('#btnRemoveModule').show();
	})
	
	$('#btnAddModule').live('click',function(){
		var $name = $('#txtModule').val(); if($name.isEmpty()) return false;	
		var course_code = ($(this).attr('name')) ? ($(this).attr('name')) : 0;
		
		if(course_code != $('#btnUpdateCourse').attr('name')){
			alert('No Course Specified for this module');
			return;
		}
		var $year = $('#txtYear').val();
		$('#btnRemoveCourse').show();
		
		var idata = {'name' : $name, 'year':$year ,'course_code':course_code,'ifunction':'AddNewModule'};
		var link = './bin/handler.php';
		$.get(link,idata,function(data){ //alert(data);
			if(data)
			$(data).prependTo('#Module_list');
		})		
	})
	
	$('.modify_data').live('click',function(){
		var code = $(this).attr('name');
		var ifunction = $(this).attr('alt');
		var idata = {'code' : code, 'ifunction':ifunction};
		var link = './bin/handler.php';		
		$.get(link,idata,function(data){
			if(data == 1){ //course
				$('#select_course').find('#' + code).remove();
			}else if(data == 2){
				$('#Module_list').find('#' + code).remove();
			}
		})	
		
	})
	
	$("#select_inst").live('change',function(e){
		OneClick(e);
		var idata = {'val' : $(this).val(), 'ifunction':'publicGetCourse'};
		var link = './bin/handler.php';	

		
		$.get(link,idata,function(data){  
			if(data){
				$('#choice_course').html(data);
				$('.course_row').show();
			}else 
						$('.module_row,.course_row').hide();
		
		})			
	})
	
	$("#choice_course").live('change',function(e){
		OneClick(e);
		var idata = {'val' : $(this).val(), 'ifunction':'publicGetModule'};
		var link = './bin/handler.php';	
		$.get(link,idata,function(data){ //alert(data);
			if(data){
				$("#show_module").html(data)
				$('.module_row').show();
			}else 
				$('.module_row').hide();
			
		})	
	})
	
	$("#country").live('change',function(){
		var $value = $(this).val().toLowerCase();
		$('.module_row').hide();
		var nipattern = new RegExp("(secondary school)","i");
		var ghpattern = new RegExp("(high school)","i");
		
		if($value == 'ghana'){
			$('.school_option').each(function(){
				var $text = $(this).text();
				if(nipattern.test($text))
					$(this).hide();
				else
					$(this).show();
			})			
		}else if($value == 'nigeria'){
			$('.school_option').each(function(){
				var $text = $(this).text();
				if(ghpattern.test($text))
					$(this).hide();
				else
					$(this).show();
			})	
		}else{
			$('.school_option').each(function(){
				var $text = $(this).text();
			
				if(nipattern.test($text) || ghpattern.test($text))
					$(this).hide();
				else
					$(this).show();
			})	
		}
	

	})

	
	$('#content').submit(function(e){ 
		var bool = false;
		$('.validate_me').each(function(){
			var $field_value = $(this).val();
			
			if($field_value.isEmpty()){
				$(this).css('background','#400'); bool = true;
			}else if($(this).attr('id') == 'email'){
				var reg = new RegExp("^([_a-z0-9-]+)(\.[_a-z0-9-]+)*@([a-z0-9-]+)(\.[a-z0-9-]+)*(\.[a-z]{2,6})$","i");
				if(!$field_value.match(reg)){
					$(this).css('background','#400');	bool = true;
				}
			}else 
				$(this).css('background','#2c343c');
		})
		if(bool) OneClick(e);
	})
	
	$('.validate_me').focus(function(e){
		$(this).css('background','#2c343c');
	})	

	

})