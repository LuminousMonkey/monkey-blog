---
title: Magic Binary Numbers
date: 2014-08-18T19:35:00Z
description: The lost art of some binary tricks in computing.
tags: Programming LowLevel Binary
---

How would you reverse a binary sequence? Say, given a 16-bit number?

<pre class="src"><code><span class="type">unsigned</span> <span class="type">int</span> <span class="variable-name">v</span>;     <span class="comment-delimiter">// </span><span class="comment">input bits to be reversed
</span><span class="type">unsigned</span> <span class="type">int</span> <span class="variable-name">r</span> = v; <span class="comment-delimiter">// </span><span class="comment">r will be reversed bits of v; first get LSB of v
</span><span class="type">int</span> <span class="variable-name">s</span> = <span class="keyword">sizeof</span>(v) * CHAR_BIT - 1; <span class="comment-delimiter">// </span><span class="comment">extra shift needed at end
</span>
<span class="keyword">for</span> (v &gt;&gt;= 1; v; v &gt;&gt;= 1)
{
  r &lt;&lt;= 1;
  r |= v &amp; 1;
  s--;
}
r &lt;&lt;= s; <span class="comment-delimiter">// </span><span class="comment">shift when v's highest bits are zero</span></code></pre>

<pre class="src"><code><span class="type">unsigned</span> <span class="type">int</span> <span class="variable-name">v</span>; <span class="comment-delimiter">// </span><span class="comment">32-bit word to reverse bit order
</span>
<span class="comment-delimiter">// </span><span class="comment">swap odd and even bits
</span>v = ((v &gt;&gt; 1) &amp; 0x55555555) | ((v &amp; 0x55555555) &lt;&lt; 1);
<span class="comment-delimiter">// </span><span class="comment">swap consecutive pairs
</span>v = ((v &gt;&gt; 2) &amp; 0x33333333) | ((v &amp; 0x33333333) &lt;&lt; 2);
<span class="comment-delimiter">// </span><span class="comment">swap nibbles ...
</span>v = ((v &gt;&gt; 4) &amp; 0x0F0F0F0F) | ((v &amp; 0x0F0F0F0F) &lt;&lt; 4);
<span class="comment-delimiter">// </span><span class="comment">swap bytes
</span>v = ((v &gt;&gt; 8) &amp; 0x00FF00FF) | ((v &amp; 0x00FF00FF) &lt;&lt; 8);
<span class="comment-delimiter">// </span><span class="comment">swap 2-byte long pairs
</span>v = ( v &gt;&gt; 16             ) | ( v               &lt;&lt; 16);</code></pre>
