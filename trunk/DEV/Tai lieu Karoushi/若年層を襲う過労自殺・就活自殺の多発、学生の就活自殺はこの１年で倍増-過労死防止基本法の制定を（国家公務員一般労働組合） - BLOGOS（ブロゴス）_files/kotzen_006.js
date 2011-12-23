kotzen.auth = { 
    ready_register_hook: function(){

    },
    load_profile: function(){
        $.ajax({
            type: "GET",
            cache : false,
            url: "/api/me/",
            dataType:"json",
            success: function(json){
                if(!json.error){
                    if(json.is_login){
                        $('#header_profile').html(kotzen.utils.tmpl('tmpl_header_profile',{ item : json.item} ));


                        if( document.getElementById("siteparts_join_member") ){
                            $('#siteparts_join_member').hide();
                        }

                        if(typeof ready_authorizer == "function") {
                            ready_authorizer(true,json.item);
                            return;
                        }
                    }
                    else {
                        if( document.getElementById("siteparts_join_member") ){
                            $('#siteparts_join_member').show();
                        }
                    }

                    if( $('.my_auth').size() ) {
                        $.ajax({
                            type: "get",
                            cache : false,
                            url: "/api/my_auth/",
                            dataType:"json",
                            success: function(json){
                                if(!json.error){
                                    $('.my_auth').html(kotzen.utils.tmpl('tmpl_my_auth',{ item : json.item } ));
                                }
                            }
                        });
                    }
                }
                 if(typeof ready_authorizer == "function") {
                    ready_authorizer(false);
                 }
                return;
            }
        });
    },
    login: function() {
        $(".login").colorbox.close();
        this.load_profile();
    },
    authorized_failed: function(key,site) {
        if(key == 'NOT_FOUND'){
            $("#login_panel_error").html('<div class="errorbox"><ul class="error"><li>会員情報が見つかりませんでした。</li></ul></div>'); 
        }
        else if(key == 'ALREADY_USED') {
            $("#login_panel_error").text('別アカウントで既に認証されています');
            // for my/settings/authorization/
            $("#" + site).text('別アカウントで既に認証されています');
        }
        else if(key == 'DISABLE_USER') {
            $("#login_panel_error").html('<div class="errorbox"><ul class="error"><li>会員アカウントが無効化されております。</li></ul></div>');
        }
        else {
            $("#login_panel_error").html('<div class="errorbox"><ul class="error"><li>認証に失敗いたしました。</li></ul></div>'); 
        }
        // $(".login").colorbox.resize({ width:"50%"});
    },
    authorized: function(site) {
        $('#login_panel').html(kotzen.utils.tmpl('tmpl_authorized',{ site: site } ));
         // $(".login").colorbox.resize({ width:"50%"});
        this.load_profile();
    },
    register:function(site) {
        if( !$('.first-auth  input[name=on_accept_rule]').is(':checked') ) {
            $('#error_msg').html('<ul class="error"><li>登録には利用規約への同意が必要です。</li></ul>');
            return false;
        }

        var type_map = {
            'facebook' : 'oauth2',
            'twitter' : 'oauth',
            'livedoor' :'openid',
            'mixi' :'openid',
            'yahoo_jp' :'openid'
        };

        $.ajax({
            type: "POST",
            cache : false,
            url: "/api/auth/"+  type_map[site] + "/"+  site + "/register/" ,
            dataType:"json",
            success: function(json){
                if(!json.error){
                    $('#login_panel').html(kotzen.utils.tmpl('tmpl_complete',{ item : json.item } ));
                    // $(".login").colorbox.resize({width:"50%"});
                    kotzen.auth.load_profile();
                }
            }
        });
    }
};
