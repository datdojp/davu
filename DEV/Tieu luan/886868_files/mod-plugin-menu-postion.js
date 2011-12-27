/**
 * Created by TT97IT.
 * User: TT97IT
 * Date: 26/10/2011
 * Time: 09:45
 * Plugin menu position top or bottom.
 */
jQuery(function($){
    $('.navtop').cuteMenupostion();
});

(function($){
    $.fn.cuteMenupostion=function(){
        return this.each(function(){
            var menu = $(this),pos = menu.offset();
            $(window).scroll(function(){
                if($(this).scrollTop() > pos.top && menu.hasClass('default')){
                    menu.removeClass('default').addClass('fixed');
                } else if($(this).scrollTop() <= pos.top  && menu.hasClass('fixed')){
                    menu.removeClass('fixed').addClass('default');
                }
            });
            
        });
    }
})(jQuery);
