var _fout_ref = '';

try { _fout_ref = top.document.referrer;}
catch (e) { _fout_ref = 'err';}
if (_fout_ref == 'err') {
    try { _fout_ref = parent.document.referrer;}
    catch (e) { _fout_ref = 'err';}
}
if (_fout_ref == 'err') {
    _fout_ref = document.referrer;
}

var _fout_url = '';
try { _fout_url = top.document.URL;}
catch (e) { _fout_url = 'err';}
if (_fout_url == 'err') {
    try { _fout_url = parent.document.URL;}
    catch (e) { _fout_url = 'err';}
}
if (_fout_url == 'err') {
    _fout_url = document.URL;
}

var _fout_query = '';
var _fout_host   = (("https:" == document.location.protocol) ? "https://" : "http://") + 'js.fout.jp';


if(typeof(_fout_xid) != "undefined") {_fout_query += '&xid=' + _fout_xid;}
if(typeof(_fout_nortbsync) != "undefined") {_fout_query += '&nortbsync=' + _fout_nortbsync;}
_fout_query += '&url=' + encodeURIComponent(_fout_url) + '&ref=' + encodeURIComponent(_fout_ref);

document.write(unescape("%3Ciframe src='") + _fout_host + "/beacon.html?" + _fout_query + unescape("' width='1' height='1' style='display:none' frameborder='0'%3E%3C/iframe%3E"));
