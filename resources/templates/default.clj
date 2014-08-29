(html5 {:lang "en"}
       [:head
        [:meta {:charset "utf-8"}]
        [:title (str (if (not= (:title metadata)
                               (:site-title (static.config/config)))
                       (str (:title metadata) " | "))
                     (:site-title (static.config/config)))]
        [:link {:rel "stylesheet" :type "text/css" :href "/default.css"}]]
       [:body
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
              [:h2.entry-title (:title metadata)])
            content]]]]])
