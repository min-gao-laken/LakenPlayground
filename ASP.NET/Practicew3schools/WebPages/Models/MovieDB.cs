using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Data.Entity; // <--- 确保有这一行

namespace WebPages.Models
{
    public class MovieDB
    {
        public int ID { get; set; }
        public string Title { get; set; }
        public string Description { get; set; }

    }

    public class MovieDBContext : DbContext
    {
        public DbSet<MovieDB> Movies { get; set; }
    }
}