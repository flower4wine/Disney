function loading(){
	$("#loadingToast").show();
}

function isWeiXin(){
    var ua = window.navigator.userAgent.toLowerCase();
    if(ua.match(/MicroMessenger/i) == 'micromessenger'){
        return true;
    }else{
        return false;
    }
}

function tmsError(msg){
	alert(msg);
}

function tmsSuccess(msg){
	alert(msg);
}

function tmsInfo(msg){
	alert(msg);
}

function clientHeight(){
	return document.body.clientHeight;
}

function clientWidth(){
	return document.body.clientWidth;
}

function scrollHeight(){
	return document.body.scrollHeight;
}

