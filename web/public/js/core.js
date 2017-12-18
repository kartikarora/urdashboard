$("#generate").click(function () {
    var key = $("#apikey").val();
    if (key !== "") {
        var qrcard = $(".qrcode-card");
        var download_button = $(".download-qr");
        qrcard.empty().qrcode({width: 248, height: 248, text: key});
        qrcard.show();
        download_button.show();
        download_button.attr('download', key.substring(0, 5) + ".png");
        download_button.click(function () {
            download_button.attr('href', document.getElementsByTagName("canvas")[0].toDataURL('image/png'));
        });

    }
});