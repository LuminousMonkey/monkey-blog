---
title: Throwaway variables in C
---

I'm re-reading "Refactoring" by Martin Fowler, which in turn was the result of me re-reading Steve Yegge's blog post (unfortunately I can't remember the post). One of the common things that programmers do, that may be a sacrifice of readability for optimisation, are temporary variables. As it turns out, this is an optimisation that seems like it doesn't have to be done.

Unfortunately I don't have any large project that I can use as an example, so I've had to create my own contrived test.

<pre class="src"><code><span class="preprocessor">#include</span> <span class="string">&lt;stdio.h&gt;</span>

<span class="type">int</span> <span class="function-name">square</span>(<span class="type">int</span>);

<span class="type">int</span> <span class="function-name">main</span>()
{
  <span class="keyword">for</span> (<span class="type">int</span> <span class="variable-name">i</span> = 0; i &lt; 5; i++)
    {
      printf(<span class="string">"Result: %d\n"</span>, square(i));
    }

  printf(<span class="string">"Called again: %d\n"</span>, square(3));
}

<span class="type">int</span> <span class="function-name">square</span>(<span class="type">int</span> <span class="variable-name">toBeSquared</span>)
{
  <span class="keyword">return</span> toBeSquared * toBeSquared;
}
</code></pre>

This code doesn't have any temporary variables, because I wanted to see what the compiler would do without them. The usual case for a temporary variable is something like the following:

<pre class="src"><code>  <span class="type">int</span> <span class="variable-name">tempVariable</span> = someFunction(x);

  <span class="keyword">for</span> (<span class="type">int</span> <span class="variable-name">i</span> = 0; i &lt; tempVariable; i++)
    <span class="sp-show-pair-match">{</span>
      printf(<span class="string">"Blah: %d\n"</span>);
    <span class="sp-show-pair-match">}</span></code></pre>

Of course this is done so you're not calling someFunction on every test of the loop. But, at least with optimisations turned on, you don't have to worry.

Anyway, I compiled my contrived example with GCC:

    gcc -fverbose-asm -std=c99 -O3 blah.c -S

Checking the resulting code, we have:

<pre class="src"><code><span class="function-name">main</span>:
<span class="function-name">.LFB3</span>:
  <span class="keyword">.cfi_startproc</span>
  <span class="keyword">pushq</span> <span class="variable-name">%rbx</span>  #
  <span class="keyword">.cfi_def_cfa_offset</span> 16
  <span class="keyword">.cfi_offset</span> 3, -16
  <span class="keyword">xorl</span>  <span class="variable-name">%ebx</span>, <span class="variable-name">%ebx</span>  # i
<span class="function-name">.L2</span>:
  <span class="keyword">movl</span>  <span class="variable-name">%ebx</span>, <span class="variable-name">%esi</span>  # i, D.2001
  <span class="keyword">xorl</span>  <span class="variable-name">%eax</span>, <span class="variable-name">%eax</span>  #
  <span class="keyword">movl</span>  $.LC0, <span class="variable-name">%edi</span> #,
  <span class="keyword">imull</span> <span class="variable-name">%ebx</span>, <span class="variable-name">%esi</span>  # i, D.2001
  <span class="keyword">addl</span>  $1, <span class="variable-name">%ebx</span>  #, i
  <span class="keyword">call</span>  printf  #
  <span class="keyword">cmpl</span>  $5, <span class="variable-name">%ebx</span>  #, i
  <span class="keyword">jne</span> .L2 #,
  <span class="keyword">movl</span>  $9, <span class="variable-name">%esi</span>  #,
  <span class="keyword">movl</span>  $.LC1, <span class="variable-name">%edi</span> #,
  <span class="keyword">xorl</span>  <span class="variable-name">%eax</span>, <span class="variable-name">%eax</span>  #
  <span class="keyword">call</span>  printf  #
  <span class="keyword">xorl</span>  <span class="variable-name">%eax</span>, <span class="variable-name">%eax</span>  #
  <span class="keyword">popq</span>  <span class="variable-name">%rbx</span>  #
  <span class="keyword">.cfi_def_cfa_offset</span> 8
  <span class="keyword">ret</span>
  <span class="keyword">.cfi_endproc</span>
<span class="function-name">.LFE3</span>:
  <span class="keyword">.size</span> main, .-main
  <span class="keyword">.section</span>  .text.unlikely
<span class="function-name">.LCOLDE2</span>:
  <span class="keyword">.section</span>  .text.startup
<span class="function-name">.LHOTE2</span>:
  <span class="keyword">.section</span>  .text.unlikely
<span class="function-name">.LCOLDB3</span>:
  <span class="keyword">.text</span>
<span class="function-name">.LHOTB3</span>:
  <span class="keyword">.p2align</span> 4,,15
  <span class="keyword">.globl</span>  square
  <span class="keyword">.type</span> square, @function
<span class="function-name">square</span>:
<span class="function-name">.LFB4</span>:
  <span class="keyword">.cfi_startproc</span>
  <span class="keyword">movl</span>  <span class="variable-name">%edi</span>, <span class="variable-name">%eax</span>  # toBeSquared, D.2006
  <span class="keyword">imull</span> <span class="variable-name">%edi</span>, <span class="variable-name">%eax</span>  # toBeSquared, D.2006
  <span class="keyword">ret</span>
  <span class="keyword">.cfi_endproc</span>
</code></pre>

Well, we have a function that gets made inline inside the for loop, and looking at my call to the function outside the loop, we can see it doesn't even bother, it just loads the value 9.

Now, this is a very simple function, and I would have to do more tests, but it would appear that the performance of certain refactorings, in particular ones involving replacing math and Boolean operations should not be a concern.

Write it as clear as possible, even to the extent of using a function just so you can give Boolean conditions a better name, and don't bother to use temp variables to cache results to avoid what you might think will be a function call. The compiler probably does it anyway.
