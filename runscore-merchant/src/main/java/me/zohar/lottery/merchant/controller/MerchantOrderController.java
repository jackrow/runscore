package me.zohar.lottery.merchant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import me.zohar.lottery.common.vo.Result;
import me.zohar.lottery.config.security.MerchantAccountDetails;
import me.zohar.lottery.merchant.param.PlatformOrderQueryCondParam;
import me.zohar.lottery.merchant.param.StartOrderParam;
import me.zohar.lottery.merchant.service.MerchantOrderService;

@Controller
@RequestMapping("/merchantOrder")
public class MerchantOrderController {

	@Autowired
	private MerchantOrderService platformOrderService;

	@GetMapping("/findMerchantOrderByPage")
	@ResponseBody
	public Result findMerchantOrderByPage(PlatformOrderQueryCondParam param) {
		MerchantAccountDetails user = (MerchantAccountDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		param.setPlatformName(user.getMerchantName());
		return Result.success().setData(platformOrderService.findMerchantOrderByPage(param));
	}

	@GetMapping("/merchantCancelOrder")
	@ResponseBody
	public Result merchatCancelOrder(String id) {
		MerchantAccountDetails user = (MerchantAccountDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		platformOrderService.merchatCancelOrder(user.getMerchantId(), id);
		return Result.success();
	}

	@GetMapping("/findMerchantOrderDetailsById")
	@ResponseBody
	public Result findMerchantOrderDetailsById(String orderId) {
		return Result.success().setData(platformOrderService.findMerchantOrderDetailsById(orderId));
	}

	@PostMapping("/startOrder")
	@ResponseBody
	public Result startOrder(StartOrderParam param) {
		MerchantAccountDetails user = (MerchantAccountDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		param.setSecretKey(user.getSecretKey());
		platformOrderService.startOrder(param);
		return Result.success();
	}

}
