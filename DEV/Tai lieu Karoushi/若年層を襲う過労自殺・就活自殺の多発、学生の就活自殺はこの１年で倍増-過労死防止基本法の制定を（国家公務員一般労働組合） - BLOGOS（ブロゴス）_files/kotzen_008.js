kotzen.forum = { 
    PUBLISHING_RESTRICTION_TYPE_ALL : 0,
    PUBLISHING_RESTRICTION_TYPE_MEMBER : 1,
    PUBLISHING_RESTRICTION_TYPE_FOLLOWING : 2,
    PUBLISHING_RESTRICTION_TYPE_NONE : 3,
    /*
    load_response : function(is_login,forum_response_id){
              $.ajax({
                 type: "GET",
                 url: "/api/forum/response/" + forum_response_id +"/",
                 dataType:"json",
                 success: function(json){
                    if(!json.error){

                    console.log(json);
                    var item = json.item;
                    item.is_login = is_login;
                    var html = kotzen.utils.tmpl('tmpl_forum_item',json.item);       
                    $('#forum-response-container').html(html);
                        kotzen.karma.get('forum_response','karma-forum-response');
                        kotzen.member_relation.get('fun-forum-response');
                        if(!is_login){
                            // XXX
                            $(".login").colorbox({width:"720px", inline:true, href:"#login_panel" , opacity:0.7 });
                        }
                        else {
                            $("." + forum_id + "_forum-response-link").colorbox({width:"720px", inline:true, href:"#" + forum_id + "_forum-response-form-container" , opacity:0.7 });
                        }
                    }
                 }
               });


    },
*/
    load : function(is_login,forum_id,prefix,org_path){
            var data = {};
            if(!prefix){
                prefix = '';
            }
            else {
                data = { recent : 1 };
            }

            if(!org_path){
                org_path = '';
            }

              $.ajax({
                 type: "GET",
                 cache : false,
                 url: "/api/forum/" + forum_id +"/",
                 data: data,
                 dataType:"json",
                 success: function(json){
                    if(!json.error){
                        // 依存
                        json.is_login = is_login;
                        json.org_path = org_path;
                        $('#' +prefix +'num_response').text(json.forum.num_response);
                        var html = kotzen.utils.tmpl('tmpl_forum',json);       
                        $('#' + prefix +'forum-container').html(html);
                        $('.' + prefix + 'badge').badgetip();
                        kotzen.karma.get('forum_response','karma-forum-response');
                        kotzen.member_relation.get('fun-forum-response');
                        if(!is_login){
                            // XXX
                            $(".login").colorbox({width:"720px", inline:true, href:"#login_panel" , opacity:0.7 });
                        }
                        else {
                            $("." + forum_id + "_forum-response-link").colorbox({width:"720px", inline:true, href:"#" + forum_id + "_forum-response-form-container" , opacity:0.7 });
                        }
                    }
                 }
               });
    },
    show_more_response : function(forum_id,forum_response_id,is_login){
              $.ajax({
                 type: "GET",
                 cache : false,
                 url: "/api/forum/" + forum_id +"/parent/" + forum_response_id + "/",
                dataType:"json",
                 success: function(json){
                    if(!json.error){
                        // 依存
                        json.is_login = is_login;
                        var html = kotzen.utils.tmpl('tmpl_forum_response_response',json);       
                        $('#forum-response-' + forum_response_id + '-container').html(html);
                        $(".forum-response-link").colorbox({width:"720px", inline:true, href:"#forum-response-form-container" , opacity:0.7 });
                        kotzen.karma.get('forum_response','karma-forum-response');
                    }
                 }
            });
    },
    add : function(forum_id,id,org_path){
        $(".forum-response-link").colorbox.close();
        if(!org_path){
            org_path = '';
        }
        var form = $('#' + id);
        var data = form.serialize() + '&_token=' + $.cookie('_token');

              $.ajax({
                 type: "GET",
                 cache : false,
                 url: "/api/my/forum/"+ forum_id + "/add/",
                 data: data,
                 dataType:"json",
                 success: function(json){
                    if(!json.error){
                        if( org_path ) {
                            location.href = org_path;
                        }
                        kotzen.forum.load(true,forum_id);
                    }
                    else {
                        if( json.reason ) {
                            if(json.reason == 'FORUM_RESPONSE_MAX_NUM_RESPONES' ){
                                $('#forum-form-error').text('意見制限数'+ json.limit + '件を超えました。');
                            }
                            else {
                                $('#forum-form-error').text('エラーが発生しました。入力情報を確認してください。');
                            }
                        }
                        else {
                            $('#forum-form-error').text('エラーが発生しました。入力情報を確認してください。');
                        }
                    }
                 }
               });
    },
    who : function(forum_id,forum_response_id){
              $.ajax({
                 type: "POST",
                 cache : false,
                 url: "/api/forum/"+ forum_id + "/response/" + forum_response_id  + "/who/",
                 dataType:"json",
                 success: function(json){
                    if(!json.error){
                        if( json.on_authorized ){
                            var container = $('#forum-response-member-section-' + forum_id + '-' + forum_response_id );  
                            var container_icon = $('#forum-response-member-icon-' + forum_id + '-' + forum_response_id );  
                            container.html( '<div class="sub_wrap"><p class="prof_name"><a href="/member/'+ json.who.id + '/">' + kotzen.utils.tagSafe(json.who.name) +'</a></p></div>');
                            container_icon.attr('src',json.who.icon_url );
                            return;
                        }
                        else {
                            var container = $('#forum-response-member-unkown-' + forum_id + '-' + forum_response_id );  
                            if(json.publishing_restriction_type == kotzen.forum.PUBLISHING_RESTRICTION_TYPE_MEMBER){
                                container.html('[BLOGOS会員のみ]');
                            }
                            else if(json.publishing_restriction_type == kotzen.forum.PUBLISHING_RESTRICTION_TYPE_FOLLOWING){
                                container.html('[フォローしている方のみ]');
                            }
                            else if(json.publishing_restriction_type == kotzen.forum.PUBLISHING_RESTRICTION_TYPE_NONE){
                                container.html('[非公開]');
                            }
                        }
                    }

                 }
               });

    },
    suggestToPeople : function(){

        var data = $('#suggest_people_form').serialize() + '&_token=' + $.cookie('_token');

        $.ajax({
            type: "POST",
            cache : false,
            url: "/api/my/social/suggest_to_people/",
            dataType:"json",
            data : data,
            success: function(json){
                if(!json.error){
                    $("#people_container").html( '<h2>フォローしている人に意見を求める</h2><div class="mainpanel-body"><p class="done">チェックしたフォローしている人の意見を要望しました。</p></div>');
                }
                else {

                }
            }
        });

    },
    showPeople : function(is_login,forum_id){

        if(is_login){
            $("#suggest_people_link")
                .attr('class','')
                .colorbox({width:"720px", height:"500px",inline:true, href: '#people_container', opacity:0.7 ,
                onLoad : function(){
                    $.ajax({
                        type: "POST",
                        cache : false,
                        url: "/api/my/following/",
                        dataType:"json",
                        success: function(json){
                            if(!json.error){
                                json.forum_id = forum_id;
                                var html = kotzen.utils.tmpl('tmpl_suggest_people',json);       
                                $("#people_container").html(html);
                            }
                        }
                    });
                }
            });
        }
    }
};
