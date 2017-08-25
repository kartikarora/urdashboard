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

$("#signin").click(function () {
    var key = $("#apikey").val();
    var provider = new firebase.auth.GoogleAuthProvider();
    firebase.auth().signInWithPopup(provider).then(function (result) {
        var user = result.user;
        var keysDatabase = firebase.database();
        keysDatabase.ref('keys/' + user['uid']).set({
            "email": user['email'],
            "image": user['photoURL'],
            "apikey": key
        });
        $(".signedin-result").show();
        firebase.auth().signOut().then(function () {
        }).catch(function (error) {
            console.error(error);
        });
    }).catch(function (error) {
        console.error(error);
    });
});