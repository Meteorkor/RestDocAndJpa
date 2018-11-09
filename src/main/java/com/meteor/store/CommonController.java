package com.meteor.store;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CommonController {
	@Autowired
	private StoreModelRepository storeRepo;
	
	@ResponseBody
	@RequestMapping(value = "a", method = RequestMethod.GET)
	public String index(){
		return "index";
	}
	
	
	@RequestMapping(value = "save", method = RequestMethod.GET)
	public String save(){
		StoreModel storeModel = new StoreModel();
		long gen = System.currentTimeMillis();
//		storeModel.setKey("key" + gen);
		storeModel.setKey("key" + "1");
		storeModel.setValue(String.valueOf(gen));
		if(storeRepo.existsById(storeModel.getKey())){
//			return "redirect:/stroe/" + storeModel.getKey();
			return "forward:/store /" + storeModel.getKey();
		}else{
			StoreModel retStoreModel = storeRepo.save(storeModel);
			return "forward:/store/count"; 
		}
	}

	@ResponseBody
	@GetMapping("/store/count")
    public String count() {
		return "cnt : " + storeRepo.count();
    }
	
	@ResponseBody
	@GetMapping("/store/{key}")
    public StoreModel search(@PathVariable("key") String key) {
        StoreModel storeModel = storeRepo.findByKey(key);
        return storeModel;
    }
	
	@ResponseBody
	@GetMapping("/")
    public ResourceSupport indexA() {
        ResourceSupport index = new ResourceSupport();
        index.add(linkTo(CommonController.class).withRel("crud"));
        return index;
    }
}
