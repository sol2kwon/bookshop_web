package jpabook.jpshop.controller;

import jpabook.jpshop.domain.Item;
import jpabook.jpshop.domain.Member;
import jpabook.jpshop.domain.Order;
import jpabook.jpshop.repository.OrderSearch;
import jpabook.jpshop.service.ItemService;
import jpabook.jpshop.service.MemberService;
import jpabook.jpshop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/order")
    public String createForm(Model model){
        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members",members);
        model.addAttribute("items",items);

        return "order/orderForm";

    }

    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count){

        orderService.order(memberId,itemId,count);

        return "redirect:/orders";

    }

    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model){
      List<Order> orders = orderService.findOrders(orderSearch);
      model.addAttribute("orders",orders);
        return "order/orderList";
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String order(@PathVariable("orderId")Long orderId){
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }
}
