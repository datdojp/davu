jQuery.noConflict();
function do_add_friend_colright(target){
	var $ = jQuery;
	target = $(target);

	var id = target.find("img").attr('id');
	//alert(id);
	var temp = id.split(":");
	var userid = temp[0];
	var username = temp[1];

	$.post(BaseURL+"member.php",{
			'action':'add_friend',
			'ctrl':'userrelation',
			'userid':userid,
			'username':username
		},function(result)
		{
			if (result == 'done'){
				target.attr('class','cutebtn removeBtn');
				target.find('img.iplus-cricle:first').attr('class','ico ifriend-add');
				target.removeAttr('onclick');
				target.find('span.add_friend').html('Chờ kết bạn');
			}
			else if(result == 'error1'){
				window.location.href = BaseURL + 'no_permission.php?url='+encodeURIComponent(document.URL);
			}else{
				alert('Có lỗi trong quá trình xử lý, vui lòng thử lại sau !');
			}
			//jQuery('.btn_add_friend').prepend(result);
		}
	);
}


function load_members_positive(target){
	var $= jQuery;
	target = $(target);

	var member_container = target.parent('div:first');

	var memberinfo = member_container.attr('id');
	var membername = memberinfo.split(':')[1];
	/*var from = memberinfo.split(':')[2];*/

	$('div#list_memberpositive').addClass('dis-b');
	var ohyeah_data = $('div#list_memberpositive').find('div.ohyeah-data');
	var content = ohyeah_data.html();
	var loading = $('div#list_memberpositive').find('img.loading');

	if($.trim(content) == ''){
		loading.removeClass('hide');
		$.get(
			BaseURL + "member.php",
			{
				'action'	: 'load_members_positive',
				'ctrl'		: 'misc'
			},
			function(result){
				if (result != 'error' && result != 'error2'){
					ohyeah_data.append(result);

					$('div#list_memberpositive').find('.viewmore').attr('id','from:10');

				}else{
					alert('Có lỗi trong quá trình xử lý. Các bạn vui lòng thử lại sau');
				}

				loading.addClass('hide');
			}
		);
	}
	$('body').delegate('span.close-grp-pop','click',function(){$('div#list_memberpositive').removeClass('dis-b')});

}

function load_more_members_positive(target){
	var $= jQuery;
	target = $(target);

	var member_container = target.parent('div:first');

	var memberinfo = member_container.attr('id');

	var from = memberinfo.split(':')[1];

	target.find('img.loading').removeClass('hide');

	$.get(
		BaseURL + "member.php",
		{
			'action'	: 'load_more_member_positive',
			'ctrl'		: 'misc',
			'from'	: from
		},
		function(result){
			var viewmore = $('div#list_memberpositive').find('.viewmore');
			if (result != 'error' && result != 'error2'){
				$('div#list_memberpositive').find('ul').append(result);
				from = parseInt(from) + 10;
				id = "from:" + from.toString();


				viewmore.attr('id',id);
				viewmore.find('a').attr('onclick','load_more_members_positive(this)');


			}else{
				if(result == 'error2'){
					viewmore.html('Không còn cập nhật nào nữa.');
					/*viewmore.children('a').html('Không còn cập nhật nào nữa.');
					viewmore.children('a').removeAttr('onclick');*/
				}else{
					alert('Có lỗi trong quá trình xử lý. Các bạn vui lòng thử lại sau');
				}
			}
			target.find('img.loading').addClass('hide');
			$('body').delegate('span.close-grp-pop','click',function(){$('div#list_memberpositive').removeClass('dis-b')});
		}
	);

}