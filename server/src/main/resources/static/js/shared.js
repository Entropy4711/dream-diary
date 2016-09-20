function getUrlParam(val) {
    let result = "";
    let tmp = [];
    
    window.location.search.substr(1).split("&amp;".replace('amp;', '')).forEach(function (item) {
        tmp = item.split("=");
        
        if (tmp[0] === val) {
        	result = decodeURIComponent(tmp[1]);
        }
    });
    
    return result;
}