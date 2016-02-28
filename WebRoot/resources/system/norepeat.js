var checkSubmitFlg = false;
	function checkSubmit(){
		if (checkSubmitFlg == true){
			return false;
		}
		checkSubmitFlg = true;
		return true;
	}
	document.ondblclick =
	function docondblclick(){
		window.event.returnValue = false;
	};
	document.onclick =
		function doconclick(){
			if (checkSubmitFlg){
				window.event.returnValue = false;
			}
		};