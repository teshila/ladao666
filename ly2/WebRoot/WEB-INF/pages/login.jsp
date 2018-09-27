<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>KevCms 管理系统</title>
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="Expires" content="0" />
<style type="text/css">
html {
	
}

body {
	color: #fff;
	font-size: 14px;
	padding: 0;
	overflow: hidden;
	margin: 0;
	width: 100%;
	height: 100%;
	min-height: 400px;
	background: #2784bd url("static/images/bg_line.gif") repeat-x left top;
}

#topbg {
	z-index: -1;
	position: absolute;
	left: 0;
	width: 100%;
	height: 360px;
	background: url("static/images/bg.jpg") no-repeat center top;
}

h1 {
	font-wieght: bold;
	font-size: 20px;
	padding: 0 28px;
	margin: 0;
	text-shadow: 0 1px 2px rgba(0, 0, 0, 0.4);
}

#login_form {
	position: absolute;
	width: 500px;
}

.input {
	font-size: 22px;
	padding-left: 12px;
	padding-right: 0px;
	width: 234px;
	height: 30px;
	background: url("static/images/spacer.gif");
	color: #383838;
	outline: medium none;
	border: none;
}

.input_div {
	position: absolute;
	margin-top: 14px;
	width: 295px;
	height: 80px;
	background: url("static/images/login_input_bg.png") 0 10px;
}

.input_div input {
	position: absolute;
	top: 26px;
}

.heightlight {
	background-position: 0 -71px;
}

.input_bg_text {
	position: absolute;
	line-height: 28px;
	top: 26px;
	color: #A2AFC2;
	padding-left: 13px;
	font-size: 22px;
	white-space: nowrap;
}

.submit_bg {
	background: url("static/images/login_submit.png");
	width: 80px;
	height: 42px;
	position: absolute;
	overflow: hidden;
	margin-top: 34px;
}

.submit_text {
	position: absolute;
	text-shadow: 0 2px 1px rgba(255, 255, 255, 0.4);
	color: #4F3400;
	font-weight: bold;
	width: 80px;
	height: 42px;
	text-align: center;
	line-height: 40px;
	cursor: pointer;
	-moz-user-select: none;
	user-select: none;
	selcte: none;
	font-size: 20px;
}

.submit_bg_hover {
	background-position: 0 -80px;
}

#fog {
	z-index: -1;
	overflow-x: hidden;
	position: absolute;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
}

input:-webkit-autofill {
	-webkit-box-shadow: 0 4px 0px 1000px whitesmoke inset !important;
	margin-left: 5px;
}
</style>
<!--[if lt IE 6.9]>
    <style type="text/css">
        .input_div{
            background:none;
        }
        .submit_bg{background:none;}

        .input_bg_forie6{
            position:absolute;
            width:290px;
            height:80px;
            overflow:hidden;
        }

        .input_bg_forie6 .inputbg{
            position:absolute;
            top:10px;
            width:286px;
            height:80px;
            filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(src='static/images/login_input_bg.png');
        }

        .submit_bg_forie6{
            position:absolute;
            width:80px;
            height:42px;
            overflow:hidden;
        }
        .submit_bg_forie6 .inputbg{
            position:absolute;
            width:80px;
            height:42px;
            filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(src='static/images/login_submit.png');
        }
    </style>
    <![endif]-->
<script type="text/javascript" src="static/js/jquery-1.8.1.min.js"></script>
<script type="text/javascript" src="static/js/jquery.form.js"></script>
<script type="text/javascript">
	$(function() {
		$("#username").attr("value", "");
		$("#password").attr("value", "");
	});
	var userAgent = navigator.userAgent.toLowerCase();
	var is_opera = navigator.appName.indexOf('Opera') >= 0 ? true : false;
	var is_ie = navigator.appName == "Microsoft Internet Explorer" ? true : false;
	var ie = (userAgent.indexOf('msie') != -1 && !is_opera) && userAgent.substr(userAgent.indexOf('msie') + 5, 3);

	function heightlight(obj) {
		obj.className = 'input_div heightlight';
		if (is_ie && ie < 7) {
			var ie6bg = obj.getElementsByTagName('*')[0].getElementsByTagName('*')[0];
			ie6bg.style.top = '-71px';
		}
	}
	function offlight(obj) {
		obj.className = 'input_div';
		if (is_ie && ie < 7) {
			var ie6bg = obj.getElementsByTagName('*')[0].getElementsByTagName('*')[0];
			ie6bg.style.top = '10px';
		}
	}
	function show_hover() {
		var obj = document.getElementById('submit_bg');
		obj.className = 'submit_bg submit_bg_hover';
		if (is_ie && ie < 7) {
			var ie6bg = obj.getElementsByTagName('*')[0].getElementsByTagName('*')[0];
			ie6bg.style.marginTop = '-80px';
		}
	}
	function show_up() {
		var obj = document.getElementById('submit_bg');
		obj.className = 'submit_bg';
		if (is_ie && ie < 7) {
			var ie6bg = obj.getElementsByTagName('*')[0].getElementsByTagName('*')[0];
			ie6bg.style.marginTop = '';
		}
	}
	function input_key_down(e) {
		e = e || window.event;
		if (e.keyCode == 13) {
			show_hover();
		}
	}
	function input_key_up(e, obj, type) {
		var tobj = document.getElementById('input_default_text_' + type);
		if (obj.value == '') {
			var v = {
				'username' : '用户名',
				'password' : '密码',
				'captcha' : '验证码'
			};
			tobj.innerHTML = v[type];
		} else {
			tobj.innerHTML = '';
		}
		show_up();
	}

	$(function() {
		$("body").keydown(function(event) {
			if (event.keyCode == 13) {
				var form = $("#myform");
				check_submit(form);
			}
		});
	});

	function check_submit(form) {
		if (form.username.value == '') {
			show_up();
			form.username.select();
			return false;
		}
		if (form.password.value == '') {
			form.password.select();
			show_up();
			return false;
		}
		if (form.captcha) {
			if (form.captcha.value == '') {
				form.captcha.select();
				show_up();
				return false;
			}
		}

		var form = $('#myform');
		var ajax = {
			url : "/check_login",
			data : form.serialize(),
			type : 'POST',
			dataType : 'json',
			cache : false,
			async : false,
			success : function(data, statusText) {
				setTimeout(function() {

					if (data.status == 1000) {
						location.href = "/manage/dashboard";
					} else {
						alert(data.msg);
					}
				}, 2000);

			},
			error : function(httpRequest, statusText, errorThrown) {
				alert('数据请求时发生错误，请检查' + errorThrown);
			}
		};
		$.ajax(ajax);
		return false;

	}
</script>
</head>
<body scroll="no">
	<div id="body">
		<div id="topbg"></div>
		<table border="0" align="center" height="100%" width="500">
			<tr>
				<td></td>
			</tr>
			<tr>
				<td height="200" align="left" valign="top">
					<div style="position:absolute;text-align:left;width:400px;height:190px;margin-left:90px;background:url(static/images/admin.png) no-repeat left top;_background:none;_filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='static/images/admin.png');">
						<div style="position:absolute;margin:31px 0 0 275px;">
							<table border="0" cellpadding="0" cellspacing="0" width="30" height="45">
								<tr>
									<td align="left" valign="bottom">
										<img id="image1" style="width:38px;height:44px;" src="static/images/admin_p2.png" />
									</td>
								</tr>
							</table>
						</div>
						<div style="position:absolute;margin:28px 0 0 276px;">
							<table style="width:60px;height:50px;" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td align="left" valign="bottom">
										<img id="image2" style="width:60px;height:48px;" src="static/images/admin_p1.png" />
									</td>
								</tr>
							</table>
						</div>
						<div style="position:absolute;width:400px;height:190px;background:url(static/images/spacer.gif);"></div>
					</div>
					<script type="text/javascript">
						(function() {
							var obj1 = document.getElementById('image1');
							var obj2 = document.getElementById('image2');
							if (is_ie && ie < 7) {
								// for ie6
								var src1 = obj1.src;
								var src2 = obj2.src;
								obj1.src = obj2.src = 'static/images/spacer.gif';
								obj1.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src='" + src1 + "')";
								obj2.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src='" + src2 + "')";
							}
							var n = 0;
							var t = 1;

							var run = function() {
								obj1.style.width = (obj1.width + (t * 2)) + 'px';
								obj2.style.width = (obj2.width - (t * 2)) + 'px';
								obj2.style.height = (obj2.height - t) + 'px';
								n += t;
								if (n == 6 || n == 0) {
									t = -t;
								}
							}
							setInterval(run, 80);
						})();
					</script>
				</td>
			</tr>
			<tr>
				<td height="200" valign="top">
					<div id="login_form">
						<form method="post" id="myform" autocomplete="off" onSubmit="return check_submit(this);">
							<input type="hidden" name="_token" value="ZlOmLWJZ2zzcqwXWOFv8JtzERvUJgma47yxC7fze">
							<input type="hidden" name="forward" value="" />
							<div style="position:absolute;margin-left:-60px;">
								<h1>管理员登录</h1>
							</div>
							<div class="input_div" style="margin-left:-60px;">
								<!--[if lt IE 6.9]><div class="input_bg_forie6"><div class="inputbg"></div></div><![endif]-->
								<div style="padding:28px;">
									<div class="input_bg_text" id="input_default_text_username">用户名</div>
									<input tabindex="1" autocomplete="off" type="text" size="30" name="username" id="username" value="" class="input" onKeyDown="input_key_down(event);" onKeyUp="input_key_up(event,this,'username');" onFocus="heightlight(this.parentNode.parentNode)" onBlur="offlight(this.parentNode.parentNode)" />
								</div>
							</div>
							<div class="input_div" style="margin-left:193px;">
								<div class="input_bg_forie6">
									<div class="inputbg"></div>
								</div>
								<div style="padding:28px;">
									<div class="input_bg_text" autocomplete="off" id="input_default_text_password">password</div>
									<input tabindex="2" type="password" style="font-family:Tahoma,Simsun,Helvetica,sans-serif;" name="password" size="30" id="password" class="input" onKeyDown="input_key_down(event);" onKeyUp="input_key_up(event,this,'password');" onFocus="heightlight(this.parentNode.parentNode)" onBlur="offlight(this.parentNode.parentNode)" />
								</div>
							</div>
							<div class="submit_bg" id="submit_bg" style="margin-left:475px;">
								<!--[if lt IE 6.9]><div class="submit_bg_forie6"><div class="inputbg"></div></div><![endif]-->
								<div style="widht:1px;height:1px;overflow:hidden;">
									<input tabindex="3" onFocus="show_hover()" onBlur="show_up();" type="submit" value="_" />
								</div>
								<div class="submit_text" onMouseDown="show_hover()" onMouseUp="show_up()" onClick="var obj=document.getElementById('myform');if (check_submit(obj))obj.submit();">登录</div>
							</div>
							<div style="clear:both;height:90px;"></div>
							<div style="position:absolute;margin-left:-30px;margin-top:0px;font-size:13px;color:#ff0;"></div>
							<div style="position:absolute;margin-left:-30px;margin-top:0px;font-size:13px;color:#fff;display: block;">账号：cmd 密码：cmd</div>
						</form>
					</div>
				</td>
			</tr>
			<tr>
				<td></td>
			</tr>
		</table>
	</div>
	<script type="text/javascript">
		window.onload = function() {
			//input_key_up({'keyCode':13},document.getElementById('username'),'username');
			//input_key_up({'keyCode':13},document.getElementById('password'),'password');
			//if (document.getElementById('captcha'))
			//{
			//    input_key_up({'keyCode':13},document.getElementById('captcha'),'captcha');
			//}
			$("#username").attr("value", "");
			$("#password").attr("value", "");
			document.getElementById('username').focus();

			$("#username").val('');
			$("#password").val('');
		};
	</script>

	<div id="fog"></div>

	<script type="text/javascript">
		var cookie = {
			get : function(name) {
				var nameEQ = name + "=";
				var ca = document.cookie.split(';');
				for (var i = 0; i < ca.length; i++) {
					var c = ca[i];
					while (c.charAt(0) == ' ')
						c = c.substring(1, c.length);
					if (c.indexOf(nameEQ) == 0)
						return decodeURIComponent(c.substring(nameEQ.length, c.length));
				}
				return null;
			},
			set : function(name, value, days, path) {
				var expires = "";
				if (days) {
					var date = new Date();
					date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
					expires = "; expires=" + date.toGMTString();
				}
				;
				path = path || '/';
				document.cookie = name + "=" + encodeURIComponent(value) + expires + ";path=" + path;
			},
			del : function(name, path) {
				var exp = new Date();
				exp.setTime(exp.getTime() - 99999);
				path = path || '/';
				document.cookie = name + "=''; expires=" + exp.toGMTString() + ';path=' + path;
			}
		}

		/**
		 * @author  alteredq / http://alteredqualia.com/
		 * @author  mr.doob / http://mrdoob.com/
		 */
		Detector = {
			canvas : !!window.CanvasRenderingContext2D,
			webgl : (function() {
				try {
					return !!window.WebGLRenderingContext && !!document.createElement('canvas').getContext('experimental-webgl');
				} catch (e) {
					return false;
				}
			})(),
			workers : !!window.Worker,
			fileapi : window.File && window.FileReader && window.FileList && window.Blob,
			getWebGLErrorMessage : function() {
				var domElement = document.createElement('div');
				domElement.style.fontFamily = 'monospace';
				domElement.style.fontSize = '13px';
				domElement.style.textAlign = 'center';
				domElement.style.background = '#eee';
				domElement.style.color = '#000';
				domElement.style.padding = '1em';
				domElement.style.width = '475px';
				domElement.style.margin = '5em auto 0';
				if (!this.webgl) {
					domElement.innerHTML = window.WebGLRenderingContext ? '当前未显示动态云层效果' : '';
				}
				return domElement;
			},
			addGetWebGLMessage : function(parameters) {
				var parent, id, domElement;
				parameters = parameters || {};
				parent = parameters.parent !== undefined ? parameters.parent : document.body;
				id = parameters.id !== undefined ? parameters.id : 'oldie';
				domElement = Detector.getWebGLErrorMessage();
				domElement.id = id;
				parent.appendChild(domElement);
			}
		};
		if (Detector.webgl) {
			function set_cloud(s) {
				var path = 'index.html';
				if (s) {
					cookie.set('_disabled_cloud', '1', 60, path);
				} else {
					cookie.del('_disabled_cloud', path);
				}

				if (document.location.href.indexOf('#') != -1) {
					document.location.reload();
				} else {
					document.location = document.location;
				}
			}
			var tmpstr = '<div style="position:absolute;top:0;right:0;text-align:right;padding:3px;font-size:12px;">';
			var disabled_cloud = cookie.get('_disabled_cloud') ? true : false;
			if (!disabled_cloud) {
				tmpstr += '<a href="#" onclick="set_cloud(1);return false;" style="color:#fff;"></a>';
				var img = new Image('static/images/cloud10.png');
				setTimeout('img=null;', 2000);
				document
						.write('<script type="text/javascript" src="static/js/ThreeWebGL.js"></'+'script><script type="text/javascript" src="static/js/ThreeExtras.js"></'+'script><script type="text/javascript" src="static/js/RequestAnimationFrame.js"></'+'script><script type="text/javascript" src="static/js/Detector.js"></'+'script>');
			} else {
				tmpstr += '<a href="#" onclick="set_cloud(0);return false;" style="color:#fff;"></a>';
				Detector.webgl = false;
			}
			tmpstr += '</div>';
			document.write(tmpstr);
			tmpstr = null;
		}
	</script>
	<script type="text/javascript">
		if (Detector.webgl) {
			/**
			 * Provides requestAnimationFrame in a cross browser way.
			 * http://paulirish.com/2011/requestanimationframe-for-smart-animating/
			 */
			if (!window.requestAnimationFrame) {
				window.requestAnimationFrame = (function() {
					return window.webkitRequestAnimationFrame || window.mozRequestAnimationFrame || window.oRequestAnimationFrame || window.msRequestAnimationFrame
							|| function( /* function FrameRequestCallback */callback, /* DOMElement Element */element) {
								window.setTimeout(callback, 1000 / 60);
							};
				})();
			}
			function resizeFog() {
				document.getElementById("fog").style.cssText = "width:" + window.innerWidth + 32 + "px;height:" + document.body.scrollHeight + "px";
			}
		}
	</script>
	<script id="vs" type="x-shader/x-vertex">
    varying vec2 vUv;
    void main() {
        vUv = uv;
        gl_Position = projectionMatrix * modelViewMatrix * vec4( position, 1.0 );
    }
</script>
	<script id="fs" type="x-shader/x-fragment">
    uniform sampler2D map;
    uniform vec3 fogColor;
    uniform float fogNear;
    uniform float fogFar;
    varying vec2 vUv;
    void main() {
        float depth = gl_FragCoord.z / gl_FragCoord.w;
        float fogFactor = smoothstep( fogNear, fogFar, depth );
        gl_FragColor = texture2D( map, vUv );
        gl_FragColor.w *= pow( gl_FragCoord.z, 20.0 );
        gl_FragColor = mix( gl_FragColor, vec4( fogColor, gl_FragColor.w ), fogFactor );
    }
</script>
	<script type="text/javascript">
		// Clouds
		var container;
		var camera, scene, renderer, sky, mesh, geometry, material, i, h, color, colors = [], sprite, size, x, y, z;

		var mouseX = 0, mouseY = 0;
		var start_time = new Date().getTime();
		var windowHalfX = window.innerWidth / 2;
		var windowHalfY = document.body.clientHeight / 2;

		function init() {
			resizeFog();
			container = document.createElement('div');
			document.getElementById('fog').appendChild(container);

			camera = new THREE.Camera(10, window.innerWidth / document.body.clientHeight, 1, 3000);
			camera.position.z = 6000;
			scene = new THREE.Scene();
			geometry = new THREE.Geometry();

			var texture = THREE.ImageUtils.loadTexture('static/images/cloud10.png');
			texture.magFilter = THREE.LinearMipMapLinearFilter;
			texture.minFilter = THREE.LinearMipMapLinearFilter;

			var fog = new THREE.Fog(0x4584b4, -100, 3000);

			material = new THREE.MeshShaderMaterial({
				uniforms : {
					"map" : {
						type : "t",
						value : 2,
						texture : texture
					},
					"fogColor" : {
						type : "c",
						value : fog.color
					},
					"fogNear" : {
						type : "f",
						value : fog.near
					},
					"fogFar" : {
						type : "f",
						value : fog.far
					}
				},
				vertexShader : document.getElementById('vs').textContent,
				fragmentShader : document.getElementById('fs').textContent,
				depthTest : false
			});

			var plane = new THREE.Mesh(new THREE.Plane(64, 64));

			for (i = 0; i < 8000; i++) {
				plane.position.x = Math.random() * 1000 - 500;
				plane.position.y = -Math.random() * Math.random() * 200 - 50;
				plane.position.z = i;
				plane.rotation.z = Math.random() * Math.PI;
				plane.scale.x = plane.scale.y = Math.random() * Math.random() * 1.5 + 0.5;
				GeometryUtils.merge(geometry, plane);
			}

			mesh = new THREE.Mesh(geometry, material);
			scene.addObject(mesh);

			mesh = new THREE.Mesh(geometry, material);
			mesh.position.z = -8000;
			scene.addObject(mesh);

			renderer = new THREE.WebGLRenderer({
				antialias : false
			});
			renderer.setSize(window.innerWidth, document.body.clientHeight);
			container.appendChild(renderer.domElement);

			document.addEventListener('mousemove', onDocumentMouseMove, false);
			window.addEventListener('resize', onWindowResize, false);
		}

		function onDocumentMouseMove(event) {
			mouseX = (event.clientX - windowHalfX) * 0.25;
			mouseY = (event.clientY - 200 - windowHalfY) * 0.15;
		}

		function onWindowResize(event) {
			resizeFog();
			camera.aspect = window.innerWidth / document.body.clientHeight;
			camera.updateProjectionMatrix();
			renderer.setSize(window.innerWidth, document.body.clientHeight);
		}

		function animate() {
			requestAnimationFrame(animate);
			render();
		}

		function render() {
			position = ((new Date().getTime() - start_time) * 0.03) % 8000;
			camera.position.x += (mouseX - camera.target.position.x) * 0.01;
			camera.position.y += (-mouseY - camera.target.position.y) * 0.01;
			camera.position.z = -position + 8000;
			camera.target.position.x = camera.position.x;
			camera.target.position.y = camera.position.y;
			camera.target.position.z = camera.position.z - 1000;
			renderer.render(scene, camera);
		}

		if (Detector.webgl) {
			init();
			animate();
		}
	</script>
</body>
</html>