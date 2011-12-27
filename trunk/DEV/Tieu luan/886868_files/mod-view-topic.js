jQuery(document).ready(function ($) {
    //alert("###");
    $('.modcheck').change(function () {
        if ($(this).attr('checked') == "checked") {
            $('.f-list').has(this).addClass("modActive");
        } else {
            $('.f-list').has(this).removeClass("modActive");
        }
        var n = $(".modcheck:checked").length;
        if (n > 0) {
            $('#inlinego').val("Ok (" + n + ")");
        } else
            $('#inlinego').val("Ok");
    });
    $('#togglesubmod').click(function () {
        $('.sub-mod-list').show();
        $('body').click(function (event) {
            var $target = $(event.target);
            if ($target.parents('#togglesubmod').length == 0) {
                $('.sub-mod-list').hide();
            }
        });
    });
});
