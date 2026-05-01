using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace WebPages.Controllers
{
    public class HomeController : Controller
    {
        public ActionResult Index()
        {
            return View();
        }

        public ActionResult About()
        {
            ViewBag.Message = "Your application description page.";

            return View();
        }

        public ActionResult Contact()
        {
            ViewBag.Message = "Your contact page.";

            return View();
        }

        // 这是一个 Action（动作）
        public ActionResult Practice()
        {

            ViewBag.UserCount = 500;
            return View(); // 这会去找 Views/Home/Practice.cshtml
        }

        // 另一个 Action
        public ActionResult Welcome(String name)
        {
            // 测试：http://localhost:52825/Home/Welcome?name=Laken
            ViewBag.Name = name;
            return Content(name + " , welcome!");
        }

        public ActionResult TestRazor()
        {
            ViewBag.Message = "This is a message from the HomeController.";
            return View();
        }
    }
}