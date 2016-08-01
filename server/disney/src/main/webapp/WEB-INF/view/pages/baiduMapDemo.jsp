<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<style type="text/css">
	body, html,.map {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
	</style>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=0169614c8aa356093d46ca0ce173019d"></script>
	<!-- <script type="text/javascript" src="http://api.map.baidu.com/library/AreaRestriction/1.2/src/AreaRestriction_min.js"></script> -->
	<title>地图展示</title>
</head>
<body  onload="load()">
	<div id="baiduMap" class="map"></div>
	
	<div id="wenheMap" style="width:100%;height:100%;">
		<img  onmousewheel="return wenheMapImageZoom(this)" 	src="http://www.w3school.com.cn/i/eg_tulip.jpg"/>
		这里面放原来的车库内部导览的html标签
	</div>
</body>
</html>
<script type="text/javascript">

	function load()
	{
		document.all["wenheMap"].style.display="none"; 
				
		var x = 121.674666;
		var y = 31.147663;
		var ggPoint = new BMap.Point(x,y);
		
		
		// 百度地图API功能
		var map = new BMap.Map("baiduMap",{minZoom:15,maxZoom:19});    // 创建Map实例
		map.centerAndZoom(ggPoint, 16);  // 初始化地图,设置中心点坐标和地图级别
		map.addControl(new BMap.MapTypeControl());   //添加地图类型控件
		map.setCurrentCity("上海");          // 设置地图显示的城市 此项是必须设置的
		map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放    
		
		
		//设置marker图标为水滴
		var vectorMarker1 = new BMap.Marker(new BMap.Point(121.67482,31.139248), {
		  // 指定Marker的icon属性为Symbol
		  icon: new BMap.Symbol(BMap_Symbol_SHAPE_POINT, {
			scale: 2,//图标缩放大小
			fillColor: "red",//填充颜色
			fillOpacity: 0.8//填充透明度
		  })
		});
		map.addOverlay(vectorMarker1);
		
		var vectorMarker1 = new BMap.Marker(new BMap.Point(121.684060,31.145635), {
		  // 指定Marker的icon属性为Symbol
		  icon: new BMap.Symbol(BMap_Symbol_SHAPE_POINT, {
			scale: 2,//图标缩放大小
			fillColor: "red",//填充颜色
			fillOpacity: 0.8//填充透明度
		  })
		});
		map.addOverlay(vectorMarker1);
		
		/*
		// 设置地图显示范围
		var b = new BMap.Bounds(new BMap.Point(121.65555, 31.158835),new BMap.Point(121.691015, 31.134017));
		try {	
			BMapLib.AreaRestriction.setBounds(map, b);
		} catch (e) {
			alert(e);
		}
		*/
		
		map.addEventListener("zoomend", function () {
			var DiTu = this.getZoom();
			if(DiTu>=19){
				document.all["baiduMap"].style.display="none"; 
				document.all["wenheMap"].style.display="block"; 
				
				// 
				// document.all["baiduMap"].style.display="block"; 
			}
		});
		
		// 寻路
		var startPoint = new BMap.Point(121.673759,31.13904);
		var endPoint = new BMap.Point(121.686079,31.145102);
		var transit = new BMap.TransitRoute(map, {
			renderOptions: {map: map}
		});
		transit.search(startPoint, endPoint);
		
		//设置起点图标
		var startIcon = new BMap.Icon("https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2676684282,2871837948&fm=58", new BMap.Size(75,75)); // 建议大小 new BMap.Size(14,23)
		var endIcon = new BMap.Icon("https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=1236383068,3400244043&fm=58", new BMap.Size(75,75));
	
		//设置起终点图标
		transit.setMarkersSetCallback(function(result){
			result[0].marker.setIcon(startIcon);
			result[1].marker.setIcon(endIcon);
		})
	
		/*
		// 获取点击经纬度
		map.addEventListener("click",function(e){
			var jing_du_value = e.point.lng ;
			var wei_du_value =  e.point.lat;
			alert(e.point.lng + "," + e.point.lat);
		});
		*/
	}
	
	
	function wenheMapImageZoom(o){ 
		var zoom=parseInt(o.style.zoom, 10)||100;
		zoom+=event.wheelDelta/12;
		
		if (zoom>100){
			o.style.zoom=zoom+'%';
		}
		else{
			 document.all["baiduMap"].style.display="block"; 
			 document.all["wenheMap"].style.display="none"; 
		}
		return false;
	}
</script>
