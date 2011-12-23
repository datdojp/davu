kotzen.channel = {
    load_countdown: function(until_date_str) {
        var re = until_date_str.match(/(\d+)-(\d+)-(\d+) (\d+):(\d+):(\d+)/);
        var year   = re[1];
        var month  = re[2];
        var dates  = re[3];
        var hour   = re[4];
        var minute = re[5];

        var until_date = new Date(year,month - 1,dates,hour,minute);
        var countdown_message = '';
        var now_date = new Date();
        var time_diff = until_date - now_date;
        if( time_diff > 1000 * 60 * 60 * 24 ) {
            countdown_message = '生放送まで、あと{dn}日';
        }
        else if( time_diff < 1000 * 60 * 60 * 24 && time_diff > 1000 * 60 * 60 ) {
            countdown_message = '生放送まで、あと{hn}時間';
        }
        else {
            countdown_message = '生放送準備中';
        }
        $('.countdown').countdown({
            until: until_date,
            layout: '<p class="count_status">' + countdown_message + '</p>'
        });
        $('.countdown_sidepart').countdown({
            until: until_date,
            layout: '<p class="count_status">' + countdown_message + '</p>'
        });
        var interval = 2000;
        var tid = setInterval(function() {
            var now_date = new Date();
            if( now_date >= until_date ) {
                $('.countdown').html('<p class="count_status">生放送準備中</p>');
                clearInterval(tid);
            }
        },interval);
    },
    watch_status: function(channel_id,channel_status) {
        var interval = 60000;
        setInterval(function() {
            $.ajax({
                type: "POST",
                cache : false,
                url: "/api/channel/" + channel_id + "/status/",
                dataType:"json",
                success: function(json){
                    if(!json.error){
                        if(json.status != channel_status) {
                            window.location.reload( true );
                        }
                    }
                }
            });
        },interval);
    },
    hide_status_info: function(channel_id) {
        var interval = 30000;
        var tid = setInterval(function() {
            $.ajax({
                type: "POST",
                cache : false,
                url: "/api/channel/" + channel_id + "/status/",
                dataType:"json",
                success: function(json){
                    if(!json.error){
                        if(json.status == 2) {
                            $('.channel_status').hide("slow");
                            clearInterval(tid);
                        }
                    }
                }
            });
        },interval);
    }
};
