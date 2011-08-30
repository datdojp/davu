function changeCheckBoxStt(className, stt) {
	$("." + className).attr("checked", stt);
}

function embedClip(wrapperId, link, autoplay, width, height) {
	$("#" + wrapperId).html([
	  					"<div id='divYtVideo'>",
	  						"NO FLASH PLAYER",
	  					"</div>"
	  				].join(""));
	
    swfobject.embedSWF(link + "?autoplay=" + autoplay,
                       "divYtVideo", width, height, "8", null, null, {}, {});
}