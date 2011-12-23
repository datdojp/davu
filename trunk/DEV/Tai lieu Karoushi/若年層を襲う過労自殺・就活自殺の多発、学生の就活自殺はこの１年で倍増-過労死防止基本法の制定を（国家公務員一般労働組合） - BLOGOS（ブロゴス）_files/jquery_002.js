(function($){
    $.fn.badgetip=function(config){
        var defaults = {
            appendTo : '#body',
            box : 'badge-box'
        };

        var options=$.extend(defaults, config);

        // create badge box
        var box = document.createElement('div');

        $(box)
            .attr('id',options.box)
            .css( 'position', 'absolute' )
            .css( 'width', '310px' )
            .css( 'height', '220px' )
            .hide()
            .appendTo( options.appendTo);
     
        // private function entry ..

        function loadInfo(attr){
              $.ajax({
                 type: "POST",
                 cache : false,
                 url: "/api/member/" + attr.member_id + "/badge/",
                 data : attr,
                dataType:"json",
                 success: function(json){
                    if(!json.error){
                        $(box).html(getDescription(json.status));
                    }
                 }
                });

        }

        function getDescription(attr){
            var type = attr.level == 0 ? '0' : 'x';
            var master = {
                'moderator' : {
                    0 : '<p><b>Moderator Level：0</b></p><h5>モデレーター</h5><p>BLOGOSでは議題での意見を見守るユーザーをモデレーターとして歓迎しています。<br />違反通報の削除された回数に応じてレベルがアップします。</p>',
                    x : '<p><b>Moderator Level：level</b></p><h5>モデレーター</h5><h6 class="c-moderator">違反通知による削除された回数はnum_abusiveness_accepted_report回です。</h6><p>BLOGOSでは議題での意見を見守るユーザーをモデレーターとして歓迎しています。<br />違反通報の削除された回数に応じてレベルがアップします。</p>'
                },
                'opinion_leader' : {
                    0 : '<p><b>Opinion Reader Level：0</b></p><h5>オピニオンリーダー</h5><p>BLOGOSでは良質の記事や意見を提供できるオピニオンリーダーを歓迎しています。<br />同意された回数に応じてレベルがアップします。</p>',
                    x : '<p><b>Opinion Reader Level：level</b></p><h5>オピニオンリーダー</h5><h6 class="c-opinion">同意された回数はnum_karma回で、num_forum_response回意見を言っています。</h6><p>BLOGOSでは良質の記事や意見を提供できるオピニオンリーダーを歓迎しています。<br />同意された回数に応じてレベルがアップします。</p>'
                },
                'evangelist' : {
                    0 : '<p><b>Evangelist Level：0</b></p><h5>エヴァンジェリスト</h5><p>BLOGOSでは記事や意見を多くの人に広め賛同されるエヴァンジェリストを歓迎しています。<br />フォロー数に応じてレベルがアップします。</p>',
                    x : '<p><b>Evangelist Level：level</b></p><h5>エヴァンジェリスト</h5><h6 class="c-evangelist">フォローしている人数がnum_following人で、フォローされている人数が num_follower人です。</h6><p>BLOGOSでは記事や意見を多くの人に広め賛同されるエヴァンジェリストを歓迎しています。<br />フォロー数に応じてレベルがアップします。</p>'
                }
            };
            var template = master[attr.badge][type];

            if(type == 'x'){
                if(attr.badge == 'moderator') {
                    template = template.replace('level',attr.level).replace('num_abusiveness_accepted_report',attr.num_abusiveness_accepted_report);
                }
                else if(attr.badge == 'opinion_leader') {
                    template = template.replace('level',attr.level).replace('num_karma',attr.num_karma).replace('num_forum_response',attr.num_forum_response);
                }
                else if(attr.badge == 'evangelist') {
                    template = template.replace('level',attr.level).replace('num_following',attr.num_following).replace('num_follower',attr.num_follower);
                }
            }
            return template;
        }

        return this.each(function(i){
            $(this).mouseenter(
                function(e){
                    var ar = $(this).attr('rel').split(':');
                    var attr = {
                        member_id : ar[0],
                        badge : ar[1]
                    };

                    var offset  = $(this).offset();
                    var adjust = $(this).width() ;

                    var leftover = $(window).width() - offset.left - $(this).width();

                    var onLeft = leftover > $(box).width() ? true : false;

                    var left = offset.left + adjust; 
                    if(!onLeft){
                       left = offset.left - $(box).width() - 42;
                       $(box).attr('class','badge-box-right');
                    }
                    else {
                       $(box).attr('class','badge-box-left');
                    }

                    $(box)
                        .css( 'top', offset.top )
                        .css( 'left',left )
                        .html('Loading...')
                        .show();

                    loadInfo(attr);
                }
            ).mouseleave(
                function(){
                    $(box).hide();
                }
            );

        });
    };
})(jQuery);
