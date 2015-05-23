 angular.module('demo.service',[])

  .factory('login_register', ['$http', function($http) { 
  
    var doRequest = function(username, path) { 
      return $http({ 
        method: 'JSONP', 
        url: 'https://api.github.com/users/' + username + '/' + path + '?callback=JSON_CALLBACK' 
      }); 
    } ;
    var login= function(username,password){
      return $http({

        method:'post',
        url:'LoginAction',
        params:{'name':username,"password":password}




      })

    };
    var register=function(username,password,phone){
      return $http({
        method:'post',
        url:'RegisterAction',
        params:{'name':username,'password':password,'phone':phone}


      })

    }

    
    return { 
      events: function(username) { return doRequest(username, 'events'); }, 
      do_login:function(username,password){ return login(username,password);},
      do_register:function(username,password,phone){return register(username,password,phone)}
     
    }; 
  }])





  .factory('userInformation', ['$http', function($http) { 
  
    var getMyAteention= function(){
      return $http({

        method:'post',
        url:'UserFocusAction',
        params:{"form":"GetFucosList"}




      })

    };
    var getAteentionMe= function(){
      return $http({

        method:'post',
        url:'UserFocusAction',
        params:{"form":"GetWhofucos"}
      })

    };
   

    
    return { 
    do_getMyAteention:function(){return getMyAteention();}
    ,
    do_getAteentionMe:function(){return getAteentionMe();}
    }
  
  }])
  
  .factory('ThemeListInformation', ['$http', function($http) { 
  
    var getThemeList= function(){
      return $http({

        method:'post',
        url:'ThemeListAction',
        params:{"CircleId":"10000"}

      })

    };
   
    
    return { 
    do_getThemeList:function(){return getThemeList();}
    }
  
  }])
  
  .factory('ThemeInformation', ['$http', function($http) { 
  
    var getTheme= function(){
      return $http({

        method:'post',
        url:'ThemeinfoAction',
        params:{"CircleId":"1","ThemeId":"1"}

      })

    };
    
    var getAnswer= function(){
      return $http({

        method:'post',
        url:'GetThemRyAction',
        params:{"ThemeId":"1"}

      })

    };
   
    
    return { 
    do_getTheme:function(){return getTheme();},
    do_getAnswer:function(){return getAnswer();}
    }
  
  }])
 
 .factory('PeopleInformation', ['$http', function($http) { 
	  
	    var getInformation= function(){
	      return $http({

	        method:'post',
	        url:'GetUsernuminfoAction',
	      })

	    };
	    
	    var getSchoolInformation= function(){
		      return $http({
		        method:'post',
		        url:'CollelabelAction',
		        params:{"form":"query"}
		      })

		    };
 
	    return { 
	    do_getInformation:function(){return getInformation();},
	    do_getSchoolInformation:function(){return getSchoolInformation();}
	    }
	  
	  }]); 