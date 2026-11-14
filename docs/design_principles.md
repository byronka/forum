Design principles
==================

These principles could easily be written at too high a level ("be sustainable"), so instead
I will write at a more practical and pragmatic theme, so that the intention
behind eccentricities is clearer.

- [ ] The application should function properly in text browsers for regular users.
  - This guarantees performance and accessibility and makes end-to-end thorough testing easier.
  - By targeting a text browser, our program focuses on a more sustainable browser paradigm.
  - My forecast is that the paradigm of constant improvements to browsers will eventually cause a toppling-over
    from the complexity. The industry will then move on to the next concept, discarding the web. Since
    the plan is for this software to last decades, the focus has to be on systems that will be around for that time.
- [ ] It should be made possible to perform most administrative functions in a text browser.
- [ ] The application should work well in multiple form factors, particularly for the desktop
  browser, tablet, and mobile phone.
- [ ] There should be a focus on maintainability - it should be fast and easy to find and 
  fix problems.
  - There are always bugs, but designing a system to find and correct bugs faster means 
    the system will stand as a paragon of quality.
  - The system is built to last a very long time and to adapt to changing circumstances. In
    this case, maintainability is paramount.
- [ ] Sending the program through all necessary testing and to production should be fast.
- [ ] Sufficient automation to enable minimal manual maintenance needed.
  - Documentation is valuable, but if possible, obviate it with automation.
- [ ] Should not require or provide cookies for unauthenticated users.
  - For typical users there is no state, and we want to make clear we are not tracking them.
- [ ] Minimal dependencies - application should be a single binary with the fewest-possible 
  third-party dependencies in the system.
  - A single binary is easiest to maintain.  Third-party dependencies add their own paradigms
    of maintainability, which would mostly lower the average.  For those reasons, before 
    choosing to incorporate a dependency, think long and hard about whether it is possible
    to achieve the goal without it.
