   /* global jQuery, Coral */
(function($, Coral) {
    "use strict";
    console.log(" --------Article Details Client Js Loaded------- ");

 //we are registring granite UI validation
 var registry = $(window).adaptTo("foundation-registry");

 /* registry.register("foundation.validation.validator", {
        selector: "[data-validation=article-title]",
        validate: function(element) {
            let textField = $(element);
            let articleTitle = textField.val();
            let pattern = /[0-9a-z]/;

            if(pattern.test(articleTitle)){
               return "Only Alphabates are allowed as Title...!";
            }

        }
    });

    //Author Address Multi Field Validation
    registry.register("foundation.validation.validator", {
        selector: "[data-validation=author-address]",
        validate: function(element) {
            let addressMF = $(element);
            let minItems = addressMF.data("min-items");
            let maxItems = addressMF.data("max-items");
            let childItemssize =  addressMF.children("coral-multifield-item").length;
            //console.log("allowed minItems :: "+minItems);
            //console.log("allowed maxItems :: "+maxItems);
            //console.log("allowed childItemssize :: "+childItemssize);
            let items = addressMF.children("coral-multifield-item");
            if(childItemssize > maxItems){
            items.last().remove();
            return "Maxmium "+maxItems +" Address Only Allowed";
            }
            if(childItemssize < minItems){
                return "Minimum "+minItems +" Address are Required";
            }
        }
    });

*/


})(jQuery, Coral);