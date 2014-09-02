---
title: Trying to understand activation records.
---

It's interesting to see the development of programming languages over time, in particular to see how programming languages influenced the development of the underlying hardware. I'm currently studying programming languages at Uni, and the majority of the course is the study of past programming languages. We've started off with Pseudo-codes (very early assembly type language), and progressed from there. FORTRAN, ALGOL, Pascal, and some other languages mentioned off-hand.

It's interesting to note that early computers didn't have the concept of the stack, that wonderful little memory space that the processor can use for keeping track of where it's been, and what it's doing (in the context of the current running process). The concept of the stack came about as the result of programming languages. In particular, the block structure of ALGOL called for the tracking of both static and dynamic "activation records". Best I can figure out is that this is what's known as the [call stack](http://en.wikipedia.org/wiki/Call_stack).

But, I'm just not quite sure. In particular the idea of traversing the stack to go and find variables that are outside the scope of the current block. I know that global variables cover this, but, as far as I'm aware, the direct address of the global variable is used. So, to help with my understanding, I wrote a [contrived C program](https://gist.github.com/LuminousMonkey/eb47b9b1f283d1d8838b) and would go through the assembly code that resulted.

I compiled with GCC on an Arch Linux install, with 64bit compilation. Just using -S to generate the [assembly in AT&T syntax](https://gist.github.com/LuminousMonkey/d2ecacc615cb798a8f16). (operator source destination)

When I say contrived, I mean it, even if variables within a block have the same name, the compiler will just allocate them on the stack normally. I wanted to test to see if statement blocks created an activation record, so I have:

<pre class="src"><code>{
    <span class="type">int</span> <span class="variable-name">x</span> = 22;
    printf(<span class="string">"main inner block x: %d\n"</span>, x);
    printf(<span class="string">"main inner block y: %d\n"</span>, y);
    x += z;

    printf(<span class="string">"main inner block x after z added: %d\n"</span>, x);
}</code></pre>

Right in the middle of my main. I suspect that the x variable, is just logically scoped. By that I mean that no activation record would be created, because it wouldn't be needed, x is just a symbol of reference in the source code.

Looking at the assembly, we can see that we have that inner statement block labelled:

<pre class="src"><code><span class="function-name">.LBB2</span>:
    <span class="keyword">movl</span>    $22, -12(<span class="variable-name">%rbp</span>)  <span class="comment-delimiter">// </span><span class="comment">Inner statement block, x = 22.
</span>    <span class="keyword">movl</span>    -12(<span class="variable-name">%rbp</span>), <span class="variable-name">%eax</span> <span class="comment-delimiter">// </span><span class="comment">Set up parameters for function call.
</span>    <span class="keyword">movl</span>    <span class="variable-name">%eax</span>, <span class="variable-name">%esi</span>
    <span class="keyword">movl</span>    $.LC3, <span class="variable-name">%edi</span>
    <span class="keyword">movl</span>    $0, <span class="variable-name">%eax</span>
    <span class="keyword">call</span>    printf          <span class="comment-delimiter">// </span><span class="comment">Save next address onto stack, jump to printf.
</span>    <span class="keyword">movl</span>    -8(<span class="variable-name">%rbp</span>), <span class="variable-name">%eax</span>  <span class="comment-delimiter">// </span><span class="comment">Set up parameters for function call.
</span>    <span class="keyword">movl</span>    <span class="variable-name">%eax</span>, <span class="variable-name">%esi</span>
    <span class="keyword">movl</span>    $.LC4, <span class="variable-name">%edi</span>
    <span class="keyword">movl</span>    $0, <span class="variable-name">%eax</span>
    <span class="keyword">call</span>    printf          <span class="comment-delimiter">// </span><span class="comment">Save next address onto stack, jump to printf.
</span>    <span class="keyword">movl</span>    -16(<span class="variable-name">%rbp</span>), <span class="variable-name">%eax</span> <span class="comment-delimiter">// </span><span class="comment">Load z into eax.
</span>    <span class="keyword">addl</span>    <span class="variable-name">%eax</span>, -12(<span class="variable-name">%rbp</span>) <span class="comment-delimiter">// </span><span class="comment">x += z
</span>    <span class="keyword">movl</span>    -12(<span class="variable-name">%rbp</span>), <span class="variable-name">%eax</span> <span class="comment-delimiter">// </span><span class="comment">Set up parameters for function call.
</span>    <span class="keyword">movl</span>    <span class="variable-name">%eax</span>, <span class="variable-name">%esi</span>
    <span class="keyword">movl</span>    $.LC5, <span class="variable-name">%edi</span>
    <span class="keyword">movl</span>    $0, <span class="variable-name">%eax</span>
    <span class="keyword">call</span>    printf          <span class="comment-delimiter">// </span><span class="comment">Save next address onto stack, jump to printf.
</span><span class="function-name">.LBE2</span>:</code></pre>

We can see that 22 is being loaded onto our stack, the "movl $22, -12(%rbp)" bit. The stack grows down, and the rbp register holds current address of the stack frame (activation record), -12 being the index.

The variable x that is scoped outside this block, in the main function itself has the index -4. So this means we have the following stack:

<img src="images/2014-09-01/stack_pic_1.png" width="640" height="384">

All through this bit of code we can't see anything other than indexed stack load and stores, so this means no activation records. With a call to a function (call outerFunction), we will see the creation of a new stack frame.

<pre class="src"><code><span class="function-name">outerFunction</span>:
<span class="function-name">.LFB0</span>:
    <span class="keyword">pushq</span>   <span class="variable-name">%rbp</span>            <span class="comment-delimiter">// </span><span class="comment">Push the previous ref to stackframe on stack.
</span>    <span class="keyword">movq</span>    <span class="variable-name">%rsp</span>, <span class="variable-name">%rbp</span>      <span class="comment-delimiter">// </span><span class="comment">Save this function's stack frame
</span>                            <span class="comment-delimiter">// </span><span class="comment">ref into rbp.
</span>    <span class="keyword">subq</span>    $16, <span class="variable-name">%rsp</span>       <span class="comment-delimiter">// </span><span class="comment">Reserve 16 bytes of space. (Pad stack frame).
</span>    <span class="keyword">movl</span>    $88, -4(<span class="variable-name">%rbp</span>)   <span class="comment-delimiter">// </span><span class="comment">z = 88
</span>    <span class="keyword">movl</span>    -4(<span class="variable-name">%rbp</span>), <span class="variable-name">%eax</span>  <span class="comment-delimiter">// </span><span class="comment">Call to printf.
</span>    <span class="keyword">movl</span>    <span class="variable-name">%eax</span>, <span class="variable-name">%esi</span>      <span class="comment-delimiter">// </span><span class="comment">Parameter passing on registers.
</span>    <span class="keyword">movl</span>    $.LC0, <span class="variable-name">%edi</span>
    <span class="keyword">movl</span>    $0, <span class="variable-name">%eax</span>
    <span class="keyword">call</span>    printf          <span class="comment-delimiter">// </span><span class="comment">Pushes address of next instruction onto
</span>                            <span class="comment-delimiter">// </span><span class="comment">the stack and jumps to printf
</span>    <span class="keyword">leave</span>                   <span class="comment-delimiter">// </span><span class="comment">Move rbp to rsp, and pop off the
</span>                            <span class="comment-delimiter">// </span><span class="comment">stack into rbp.
</span>    <span class="keyword">ret</span>                     <span class="comment-delimiter">// </span><span class="comment">Pop address off the stack and
</span>                            <span class="comment-delimiter">// </span><span class="comment">return to it.</span></code></pre>

Concerning stack frames, we only care about stack operations and things involving the rsp, and rbp registers. With the Linux ABI, it is the responsibility of the function being called to save things correctly before proceeding.

With the "pushq %rbp" instruction, it's saving the position of the stack frame of the calling function. This is done at the front of every function, it then takes the current value of the stack (rsp) and copies it into the stack frame register (rbp). This means that rbp can then be used for referring to anything local to the functions stack frame.

Pushing onto the stack advances the stack and copies the value into the resulting memory address. Since the stack frame is copied into the rbp register this means that rbp points to the calling functions stack frame.

<img src="images/2014-09-01/stack_pic_2.png" width="640" height="651">

However, we are still not getting any traversal of any of these stack frames, at least nothing that would indicate anything about dynamic or static activation records. The problem seems to be that you need to try and access variables outside your scope across stack frame boundaries. You can't do this with C, sure, you have global scope, but that doesn't have a stack frame, any globals will be referenced directly.

So, what to do? Well to force the issue of a function using a variable out of its local scope, but not using the global scope, I've had to use a GCC trick.

I have a function declared inside another function. This is not standard C:

<pre class="src"><code>  <span class="comment-delimiter">// </span><span class="comment">In main()
</span>  <span class="type">void</span> <span class="variable-name">innerFunction</span>() {
    printf(<span class="string">"Inner function z: %d\n"</span>, z);
  }

  innerFunction();
</code></pre>
