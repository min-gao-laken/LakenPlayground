using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Mvc;
using WebPages.Models;

namespace WebPages.Controllers
{
    public class MovieDBsController : Controller
    {
        private MovieDBContext db = new MovieDBContext();

        // GET: MovieDBs
        public ActionResult Index()
        {
            return View(db.Movies.ToList());
        }

        // GET: MovieDBs/Details/5
        public ActionResult Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            MovieDB movieDB = db.Movies.Find(id);
            if (movieDB == null)
            {
                return HttpNotFound();
            }
            return View(movieDB);
        }

        // GET: MovieDBs/Create
        // 这个方法负责返回一个空白的 HTML 表单给用户看
        public ActionResult Create()
        {
            return View();
        }

        // POST: MovieDBs/Create
        // 这个方法负责接收用户填完后提交过来的数据
        // To protect from overposting attacks, enable the specific properties you want to bind to, for 
        // more details see https://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken] // 安全检查，防止跨站请求伪造
        public ActionResult Create([Bind(Include = "ID,Title,Description")] MovieDB movieDB)
        {
            if (ModelState.IsValid)
            {
                db.Movies.Add(movieDB);
                db.SaveChanges();
                return RedirectToAction("Index");
            }

            return View(movieDB);
        }

        // GET: MovieDBs/Edit/5
        public ActionResult Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            MovieDB movieDB = db.Movies.Find(id);
            if (movieDB == null)
            {
                return HttpNotFound();
            }
            return View(movieDB);
        }

        // POST: MovieDBs/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to, for 
        // more details see https://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit([Bind(Include = "ID,Title,Description")] MovieDB movieDB)
        {
            if (ModelState.IsValid)
            {
                db.Entry(movieDB).State = EntityState.Modified;
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            return View(movieDB);
        }

        // GET: MovieDBs/Delete/5
        public ActionResult Delete(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            MovieDB movieDB = db.Movies.Find(id);
            if (movieDB == null)
            {
                return HttpNotFound();
            }
            return View(movieDB);
        }

        // POST: MovieDBs/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(int id)
        {
            MovieDB movieDB = db.Movies.Find(id);
            db.Movies.Remove(movieDB);
            db.SaveChanges();
            return RedirectToAction("Index");
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        // 1. 访问路径将会是 /MovieDBs/Search
        [HttpGet] // 明确告诉系统：这个方法只响应 GET 请求
        public ActionResult Search(string movieName)
        {
            // 这里的 movieName 会自动接收 URL 里的参数，例如 ?movieName=Inception
            ViewBag.Message = "正在搜索的电影是：" + movieName;

            var results = db.Movies.Where(m => m.Title.Contains(movieName)).ToList();
            return View(results);
            //return View(); // 这行代码会去找名为 Search.cshtml 的视图文件
        }

        [HttpPost]
        public ActionResult Search(string movieName, string description)
        {
            ViewBag.Message = $"正在搜索的电影是：{movieName}，描述包含：{description}";
            // 把搜索词传回页面，方便在搜索框里保留刚才输入的字
            ViewBag.LastSearch = movieName;
            ViewBag.LastDescription = description;
            var results = db.Movies.Where(m => m.Title.Contains(movieName) && m.Description.Contains(description)).ToList();
            return View(results);
        }
    }
}
