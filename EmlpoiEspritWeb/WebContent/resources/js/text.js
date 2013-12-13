
    function start() {  
        PF('startButton1').disable();  
  
        window['progress'] = setInterval(function() {  
            var pbClient = PF('pbClient'),  
            oldValue = pbClient.getValue(),  
            newValue = oldValue + 10;  
  
            pbClient.setValue(pbClient.getValue() + 10);  
  
            if(newValue === 100) {  
                clearInterval(window['progress']);  
            }  
  
  
        }, 1000);  
    }  
  
    function cancel() {  
        clearInterval(window['progress']);  
        PF('pbClient').setValue(0);  
        PF('startButton1').enable();  
    }  
