const PLAYER_INTERVAL = 500;

var aihat = new Object();

aihat.scrollTo = function(id) {
	location.href = "#" + id;
}


function selectOnChange_header(header, formId, selectClass) {
	$("#" + formId + " ." + selectClass).attr("checked", header.checked);
	$(header).css("opacity", "1");
}

function selectOnChange_item(formId, headerClass, selectClass) {
	var nChecked = 0;
	var nUnchecked = 0;
	var $header = $("#" + formId + " ." + headerClass);
	$("#" + formId + " ." + selectClass).each(
			function(index) {
				if($(this).attr("checked")) {
					nChecked++;
				} else {
					nUnchecked++;
				}
			}
	);
	if(nChecked == 0) {
		$header.attr("checked", "");
		$header.css("opacity", "1");
	} else if(nUnchecked == 0) {
		$header.attr("checked", "checked");
		$header.css("opacity", "1");
	} else {
		$header.attr("checked", "checked");
		$header.css("opacity", "0.5");
	}
}

//map that store dialog for forms
var mapFormDialog = {};

/**
 * FUNCTIONS OF CLIP-LIST-FUNC
 */
function showDlgCreateOrAddToMyPlaylists(formId) {
	if(mapFormDialog[formId]) {
		mapFormDialog[formId].dialog("open");
	} else {
		mapFormDialog[formId] =
			$("#" + formId).find(".dlgCreateOrAddToMyPlaylists").dialog({
				width:500,
				modal:true,
				draggable:true,
				resizable:true,
				show:"drop",
				hide:"drop"
			});
	}
}

function onclickCreateOrAddToMyPlaylists(formId) {
	$("#" + formId).find(".hdnSubmitCreatePlaylistAndAddClips").click();
	mapFormDialog[formId].dialog("close");
	//mapFormDialog[formId].dialog("destroy");
	mapFormDialog[formId] = null;
}

function onclickAddClipsToSelectedPlaylists(formId) {
	$("#" + formId).find(".hdnSubmitAddClipsToSelectedPlaylists").click();
	mapFormDialog[formId].dialog("close");
	//mapFormDialog[formId].dialog("destroy");
	mapFormDialog[formId] = null;
}

function onchangeNewPlaylistTitle(obj, formId) {
	var newPlaylistTitle = $(obj).val();
	$("#" + formId).find(".hdnNewPlaylistTitle").val(newPlaylistTitle);
}

function onchangeNewPlaylistDescription(obj, formId) {
	var newPlaylistDescription = $(obj).val();
	$("#" + formId).find(".hdnNewPlaylistDescription").val(newPlaylistDescription);
}

function onchangeSelectPlaylist(obj, formId, objId) {
	var checked = $(obj).attr('checked');
	$("#" + formId).find(".playlist_" + objId).attr('checked', checked);
}

function onclickProfileAvatar() {
	$("#myProfile").find(".fileProfileAvatar").click();
	$("#myProfile").find(".btnChangeAvatar").attr("disabled", "");
}

function oddEventMouseOver(line) {
	$(line).find("td").addClass("line_over");
	$(line).find(".nohover").removeClass("line_over");
}
function oddEventMouseOut(line) {
	$(line).find("td").removeClass("line_over");
}