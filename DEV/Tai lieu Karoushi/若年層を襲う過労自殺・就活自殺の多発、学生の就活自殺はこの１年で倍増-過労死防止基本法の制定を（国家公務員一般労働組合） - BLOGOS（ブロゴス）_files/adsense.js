function enable_highlight_afc(el) {
  el.style.backgroundColor = '';
}
function disable_highlight_afc(el) {
  el.style.backgroundColor = '';
}

function google_ad_request_done(google_ads) {
        var s = '';
        var i;
        var container_id = adsense_container_id;

        s += '<div class="google-body">';

        if (google_ads.length == 0) {
            return;
        }
        if (google_ads[0].type="text" && google_ads.length >= 1) {
            s += '<ul>';
            for(i=0; i < google_ads.length; ++i) {
               s += '<li onmouseover="enable_highlight_afc(this);" onmouseout="disable_highlight_afc(this);">' +
               '<a href="' + google_ads[i].url + '"><span class="ads-title">' + google_ads[i].line1 + '</span></a><br />' +
               '<a href="' + google_ads[i].url + '"><span class="ads-url">' + google_ads[i].visible_url + '</span>' +
               '<span class="ads-text">' + google_ads[i].line2 + google_ads[i].line3 + '</span></a>' +
               '</li>';
            }
            s += '</ul>';
        }
        else if (google_ads[0].type == "html") {
                s += google_ads[0].snippet;
        }
        s += '</div>';
        s += '<div class="google-title">';
        if (google_info.feedback_url) {
           s += '<span class="google"><a href="' + google_info.feedback_url + '">Ads by Google</a></span>';
        }else {
           s += '<span class="google">Ads by Google</span>';
        }
        s += '</div>';

        var targetDiv = document.getElementById(container_id);
        if ( !targetDiv ) return;
        targetDiv.innerHTML = s;
        return;
}

