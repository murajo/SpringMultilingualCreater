jQuery(function ($) {

    $('#btn-register').click(function (event) {
        registerApi();
    });
});

function registerApi() {

    $.ajax({
        type: "POST",
        cache: false,
        url: '/register/apikey',
        data: {
            'apiKey': document.getElementById('apikey').value
        },
        dataType: 'text',
    }).done(function (data) {
        if (data == "success") {
            document.getElementById('registerResult').innerHTML = '登録成功：5秒後にリダイレクトします';
            translateRedirect();
        } else {
            document.getElementById('registerResult').innerHTML = '登録失敗';
        }

    }).fail(function (jqXHR) {
        console.log(jqXHR.status);
    });
}
function translateRedirect() {
    window.setTimeout(function () {
        window.location.href = '/translate';
    }, 5000);
}