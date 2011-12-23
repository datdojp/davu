kotzen.member_relation = { 
    unfollow: function(t){
        var e = $(t);

        $.ajax({
            type: "POST",
            cache : false,
            url: "/api/member_relation/"+ e.attr('rel') + "/unfollow/",
            dataType:"json",
            data : { "_token" : $.cookie('_token') } ,
            success: function(json){
                if(!json.error){
                    if( json.code == 'DONE' ){
                        e.html( '<span class="button">フォロー中</span><span class="count"><em>' +  json.num_follower + '</em></span>'  );
                        e.attr({
                            onClick : 'kotzen.member_relation.follow(this);',
                            'class' :'follow-button'
                        });
                    }
                }
            }
        });
    },
    follow : function(t){
        var e = $(t);

        $.ajax({
            type: "POST",
            cache : false,
            url: "/api/member_relation/"+ e.attr('rel') + "/follow/",
            dataType:"json",
            data : { "_token" : $.cookie('_token') } ,
            success: function(json){
                if(!json.error){
                    if(json.on_member){
                        if( json.code == 'DONE' ){
                            e.html( '<span class="button">フォローする</span><span class="count"><em>' +  json.num_follower + '</em></span>' );
                            e.attr({
                                onClick : 'kotzen.member_relation.unfollow(this);',
                                'class' :'unfollow-button'
                            });
                        }
                    }
                    else {
                        // 認証していない
                        // XXX PCしかうごかんかも。依存してる。
                        $('.login').click();
                    }
                }
            }
        });
    },
    //  人が重複してるケースも考慮してる
    get : function(class_name,on_readonly){
        var data = {};
        var ids = [];
        $('.' + class_name ).each(function(index){
            var e = $(this);
            if(!data[e.attr('rel')]){
                data[e.attr('rel')] = []; 
                ids.push(e.attr('rel'));
            }
            data[e.attr('rel')].push(e); 
        });

        var query = 'ids=' + ids.join(',');
        $.ajax({
            type: "GET",
            cache : false,
            url: "/api/member_relation/?" + query,
            dataType:"json",
            success: function(json){
                if(!json.error){
                    var items = json.items;
                    for( var i =0 ; i < items.length;i++){
                        var ids = data[items[i].id];
                        for(var j=0; j < ids.length;j++){
                            if(on_readonly){
                                ids[j].html( '<span class="button"> </span><span class="count"><em>' + items[i].id + '</em></span>');
                            }
                            else {
                                if( items[i].is_myself ){
                                    ids[j].html('<p class="myself">あなたです</p><span id="count_myself" class="count"><em>' + items[i].num_follower + '</em></span>' );
                                }
                                else if( items[i].is_follower ){
                                    ids[j].html( '<a href="javascript:void(0);" rel="' + items[i].id+ '" onClick="kotzen.member_relation.unfollow(this);" class="unfollow-button"><span class="button">フォロー中</span><span class="count"><em>' + items[i].num_follower + "</em></span></a>" );
                                }
                                else {
                                    ids[j].html( '<a href="javascript:void(0);" rel="' + items[i].id+ '" onClick="kotzen.member_relation.follow(this);" class="follow-button" title="BLOGOSでフォローしてみましょう"><span class="button">フォローする</span><span class="count"><em>' + items[i].num_follower + "</em></span></a>" );
                                }
                            }
                        }
                    }
                }
            }

        });        
    }
};
