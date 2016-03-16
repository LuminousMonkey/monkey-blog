(html5 {:lang "en"}
       [:head
        [:meta {:charset "utf-8"}]
        [:title (str (if (not= (:title metadata)
                               (:site-title (static.config/config)))
                       (str (:title metadata) " | "))
                     (:site-title (static.config/config)))]
        [:link {:rel "stylesheet" :type "text/css" :href "/default.css"}]
        [:link {:rel "alternate" :type "application/rss+xml" :href "/rss-feed/"}]]
       [:body
        [:script {:type "text/javascript"}
         "(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-10192128-1', 'auto');
  ga('send', 'pageview');"]
        [:script {:type "text/javascript" :src "https://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML"}]
        [:div#wrapper
         [:div#masthead
          [:h1 (:site-title (static.config/config))]
          [:div#nav
           [:ul
            [:li [:a {:href "/"} "Home"]]
            [:li [:a {:href "/about.html"} "About"]]]]]
         [:div#main
          [:div#container
           [:div#content
            (if (= :post (:type metadata))
              [:div.post.hentry
               [:h2.entry-title
                [:a {:href (:url metadata)} (:title metadata)]]])
            content]]]]])
