using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using WebPages.Models;

namespace WebPages.Controllers
{
    public class AccountController : Controller
    {
        // 1. GET: /Account/Register
        [HttpGet]
        public ActionResult Register()
        {
            return View();
        }

        // 2. POST: /Account/Register
        [HttpPost]
        public ActionResult Register(RegisterViewModel model)
        {
            // 这一步最关键！MVC 会根据 Model 里的注解自动检查数据是否合法
            if (ModelState.IsValid)
            {
                // 如果合法，这里通常会写保存到数据库的逻辑
                // 现在我们先模拟成功，跳转到主页
                TempData["Message"] = "注册成功，欢迎 " + model.UserName;
                return RedirectToAction("Index", "Home");
            }

            // 如果不合法（比如密码太短），直接返回 View，MVC 会自动把错误信息带过去
            return View(model);
        }


        // GET: Account
        public ActionResult Index()
        {
            return View();
        }

        // GET: Account/Details/5
        public ActionResult Details(int id)
        {
            return View();
        }

        // GET: Account/Create
        public ActionResult Create()
        {
            return View();
        }

        // POST: Account/Create
        [HttpPost]
        public ActionResult Create(FormCollection collection)
        {
            try
            {
                // TODO: Add insert logic here

                return RedirectToAction("Index");
            }
            catch
            {
                return View();
            }
        }

        // GET: Account/Edit/5
        public ActionResult Edit(int id)
        {
            return View();
        }

        // POST: Account/Edit/5
        [HttpPost]
        public ActionResult Edit(int id, FormCollection collection)
        {
            try
            {
                // TODO: Add update logic here

                return RedirectToAction("Index");
            }
            catch
            {
                return View();
            }
        }

        // GET: Account/Delete/5
        public ActionResult Delete(int id)
        {
            return View();
        }

        // POST: Account/Delete/5
        [HttpPost]
        public ActionResult Delete(int id, FormCollection collection)
        {
            try
            {
                // TODO: Add delete logic here

                return RedirectToAction("Index");
            }
            catch
            {
                return View();
            }
        }
    }
}
