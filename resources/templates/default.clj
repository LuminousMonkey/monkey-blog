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
        [:script {:type "text/javascript" :src "http://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML"}]
        [:div#wrapper
         [:div#masthead
          [:h1 (:site-title (static.config/config))]
          [:div#nav
           [:ul
            [:li [:a {:href "/"} "Home"]]
            [:li [:a {:href "/about.html"} "About"]]]
           [:div.social-list
          [:a {:href "https://twitter.com/LuminousMonkey"}
           [:span.icon.icon--twitter
            [:svg {:viewbox "0 0 16 16"}
             [:path {:fill "#55acee" :d "M15.969,3.058c-0.586,0.26-1.217,0.436-1.878,0.515c0.675-0.405,1.194-1.045,1.438-1.809 c-0.632,0.375-1.332,0.647-2.076,0.793c-0.596-0.636-1.446-1.033-2.387-1.033c-1.806,0-3.27,1.464-3.27,3.27 c0,0.256,0.029,0.506,0.085,0.745C5.163,5.404,2.753,4.102,1.14,2.124C0.859,2.607,0.698,3.168,0.698,3.767 c0,1.134,0.577,2.135,1.455,2.722C1.616,6.472,1.112,6.325,0.671,6.08c0,0.014,0,0.027,0,0.041c0,1.584,1.127,2.906,2.623,3.206 C3.02,9.402,2.731,9.442,2.433,9.442c-0.211,0-0.416-0.021-0.615-0.059c0.416,1.299,1.624,2.245,3.055,2.271 c-1.119,0.877-2.529,1.4-4.061,1.4c-0.264,0-0.524-0.015-0.78-0.046c1.447,0.928,3.166,1.469,5.013,1.469 c6.015,0,9.304-4.983,9.304-9.304c0-0.142-0.003-0.283-0.009-0.423C14.976,4.29,15.531,3.714,15.969,3.058z"}]]]]
          [:a {:href "https://stackoverflow.com/users/841/mike"}
           [:span.icon.icon--stackoverflow
            [:svg {:viewbox "0 0 12.56 14.84"}
             [:polygon {:fill "#bcbbbb" :points "10.6 13.52 10.6 9.56 11.92 9.56 11.92 14.84 0 14.84 0 9.56 1.32 9.56 1.32 13.52 10.6 13.52"} ""]
             [:path {:fill "#f48023" :d "M4.52,9.78L11,11.14l0.28-1.28L4.8,8.5ZM5.36,6.66l6,2.8,0.56-1.2-6-2.8ZM7,3.7l5.08,4.24,0.84-1L7.88,2.7Zm3.28-3.12-1.08.8,4,5.32,1.08-.8Zm-6,12.2H11V11.46H4.36v1.32Z" :transform "translate(-1.72 -0.58)"} ""]]]]
            [:a {:href "https://gitlab.com/u/LuminousMonkey"}
             [:span.icon.icon--gitlab
              [:svg {:viewbox "0 0 500 500"}
               [:g {:fill "none" :fill-rule "evenodd"}
                [:path {:d "M495.2 280.225l-27.56-84.815-54.617-168.097c-2.81-8.648-15.046-8.648-17.856 0l-54.62 168.097H159.182L104.56 27.313c-2.808-8.648-15.044-8.648-17.855 0L32.088 195.41 4.53 280.225c-2.514 7.736.24 16.21 6.82 20.992l238.514 173.29 238.515-173.29c6.58-4.78 9.33-13.256 6.82-20.992" :fill "#fc6d26"} ""]
                [:path {:d "M249.865 474.506L340.55 195.41H159.18l90.685 279.096z" :fill "#e24329"} ""]
                [:path {:d "M249.865 474.506L159.18 195.41H32.09l217.775 279.096z" :fill "#fc6d26"} ""]
                [:path {:d "M32.09 195.41L4.53 280.226c-2.513 7.736.24 16.21 6.82 20.99l238.515 173.29L32.09 195.41z" :fill "#fca326"} ""]
                [:path {:d "M32.09 195.41h127.09L104.563 27.315c-2.81-8.65-15.047-8.65-17.856 0L32.09 195.41z" :fill "#e24329"} ""]
                [:path {:d "M249.865 474.506L340.55 195.41h127.09L249.866 474.507z" :fill "#fc6d26"} ""]
                [:path {:d "M467.64 195.41l27.56 84.815c2.512 7.736-.24 16.21-6.822 20.99l-238.514 173.29L467.64 195.41z" :fill "#fca326"} ""]
                [:path {:d "M467.64 195.41H340.548l54.62-168.096c2.81-8.65 15.046-8.65 17.855 0L467.64 195.41z" :fill "#e24329"} ""]]]
              ]]]]]
         [:div#main
          [:div#container
           [:div#content
            (if (= :post (:type metadata))
              [:div.post.hentry
               [:h2.entry-title
                [:a {:href (:url metadata)} (:title metadata)]]])
            content

            (if (= :post (:type metadata))
              [:div#disqus_thread])]]]]

        (if (= :post (:type metadata))
          [:script {:type "text/javascript"}
          "var disqus_shortname = 'luminousmonkey';
        (function() {
                var dsq = document.createElement('script'); dsq.type = 'text/javascript'; dsq.async = true;
                        dsq.src = '//' + disqus_shortname + '.disqus.com/embed.js';
                                (document.getElementsByTagName('head')[0] || document.getElementsByTagName('body')[0]).appendChild(dsq);
                                    })();
                                    </script>
                                    <noscript>Please enable JavaScript to view the <a href=\"https://disqus.com/?ref_noscript\" rel=\"nofollow\">comments powered by Disqus.</a>"])])
