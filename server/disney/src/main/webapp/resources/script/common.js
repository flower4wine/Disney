
function loading(){
	$("#loadingToast").show();
}

function endLoading(){
	$("#loadingToast").hide();
}

function domain(){
	return "http://www.shsdxg.com/";
}


//首页
//首页页面自动调整
function idxUIFit(){
	//调整底部
	if(scrollHeight() > clientHeight()){
		$(".idx_bot").css("position","relative");
	}

	//调整位置CSS
	$(".item_circle").each(function(){
		var w = $(this).css("width");
		$(this).css("height", w);
	});

	//轮播
	$(".idx_slider").unslider({
		infinite : true
	});

	//轮播滑动
	$(".idx_slider").swipe({
		swipeLeft : function(event, direction, distance, duration,
				fingerCount) {
			$(".unslider .next").click();
		},

		swipeRight : function(event, direction, distance, duration,
				fingerCount) {
			$(".unslider .prev").click();
		},

		allowPageScroll : "auto"

	});
	
	$(".slider_item").show();
}

/**********Guide Map********/
function resetGuideMap() {
	var height = clientHeight();
	var headHeight = parseInt($(".guide-ab-bottom").css("height"));
	//详情
	var detailHeight = 0;
	
	if($(".guide-top").css("height")){
		detailHeight = parseInt($(".guide-top").css("height"));
		
		if(detailHeight>0){
			//详情 的padding 8+5
			detailHeight+=13;
		}
	}
	

	var contentHeight = (height - headHeight+detailHeight) + "px";
	$(".guide-map .guide-map-container").animate({
		height : contentHeight
	});
}


function toggleGuideInOutImg(domItem){
	var o = $(".guide-map img").data("out-url");
	var i = $(".guide-map img").data("in-url");
	var src = $(".guide-map img").attr("src");
	
	if(src == o){
		 $(".guide-map img").attr("src",i);
		 /*$(domItem).html('<i class="fa fa-refresh" aria-hidden="true"></i> <span>外部导览</span>');*/
		 
		 $(domItem).find("img").attr("src",o);
		 
	}else{
		 $(".guide-map img").attr("src",o);
		 $(domItem).find("img").attr("src",i);
		 /*$(domItem).html('<i class="fa fa-refresh" aria-hidden="true"></i> <span>内部导览</span>');*/
	}
}	
/**********Guide Map********/


function wxPreViewImg(imgsrc){
	if (!isWeiXin()) {
		return;
	}
	
	var srcList = [];
	var src = imgsrc.attr('src');
	
    if(src) {
       //src = domain() + src;
       srcList.push(src);
       
	   wx.previewImage({
		    current: src, // 当前显示图片的http链接
		    urls: srcList // 需要预览的图片http链接列表
		});
   	}
}


function wxOnScan(handler){
	if (!isWeiXin()) {
		tmsError('请在微信中使用当前功能！');
		return;
	}
	
	wx.ready(function(){
		//点击扫描按钮，扫描二维码并返回结果
		wx.scanQRCode({
			needResult : 1,
			desc : 'scanQRCode desc',
			success : function(res) {
				tmsAsynchGet('/getScanCode.html?scanResult='+res.resultStr, function(response){
					if(response.data && response.data!=''){
						handler(response.data);
					}else{
						tmsError('二维码格式不正确，无法正确解析。');
					}
				});
			},
			fail:function(res){
				tmsError('执行扫码出错。');
			}
		});
	});
}

function wxConfig(timestamp,nonceStr,signature,appId){
	if (!isWeiXin()) {
		return;
	}
	
	wx.config({
		debug : false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
		appId : appId, // 必填，公众号的唯一标识
		timestamp : timestamp, // 必填，生成签名的时间戳
		nonceStr : nonceStr, // 必填，生成签名的随机串
		signature : signature,// 必填，签名，见附录1
		jsApiList : [ 'scanQRCode' ] // 需要检测的JS接口列表，所有JS接口列表见附录2,
	});
	
	wx.error(function(res){
		console.log('微信配置出错。');
	});
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

function tmsAppPath(){
	return "/disney";
}

function tmsUrl(url){
	return tmsAppPath() + url;
}


function tmsAsynchGet(url,callback){
	tmsAsynch(tmsUrl(url),'GET',null,callback);
}

function tmsAsynchPost(url,param,callback){
	tmsAsynch(tmsUrl(url),'POST',param,callback);
}


function tmsAsynch(url,method,param,callback){
	$.ajax({
		type:method,
		url:url,
		data:param,
		success:function(response,status,xhr){
			if(xhr.status == 200){
				if(response.success){
					callback(response);
				}else{
					tmsError(response.message);
				}
			}
        },
        error:function(xhr,status,statusText){
        	tmsError('服务器内部错误:'+statusText);
        }  
	});
}

















