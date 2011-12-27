/*

FREESTYLE MENUS v1.0 RC (c) 2001-2009 Angus Turnbull, http://www.twinhelix.com
Altering this notice or redistributing this file is prohibited.

*/
function muare_show_menu (id,obj){
		document.getElementById(id).style.display='block';
		if(obj.className == 'per_button'){
			obj.className = 'per_button_hover';
		}
		
}

function muare_hide_menu (id,obj){
		document.getElementById(id).style.display='none';
		if(obj.className == 'per_button_hover'){
			obj.className = 'per_button';
		}
		
}