{:title "Just me, talking crap"}

[:ul {:class "posts"}
 (map
  #(let [f %
         url (static.core/post-url f)
         [metadata _] (static.io/read-doc f)
         date (static.core/parse-date
               "yyy-MM-dd" "dd MMM yyyy"
               (re-find #"\d*-\d*-\d*" (str f)))]
     [:li [:span date] [:a {:href (str url "#disqus_thread")} (:title metadata)]])
  (take 25 (reverse (static.io/list-files :posts))))]

[:script "text/javascript"
 "var disqus_shortname = 'luminousmonkey';(function () {
        var s = document.createElement('script'); s.async = true;
        s.type = 'text/javascript';
        s.src = '//' + disqus_shortname + '.disqus.com/count.js';
        (document.getElementsByTagName('HEAD')[0] || document.getElementsByTagName('BODY')[0]).appendChild(s);
    }());"]
