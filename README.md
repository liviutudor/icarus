# icarus
Server side image generation in Java

Named after the [Greek mythology character Icarus](https://en.wikipedia.org/wiki/Icarus) on the basis that he had a better picture of the world after building the wings and flying up -- and we are dealign with pictures here :)

## Intent

The project started as a way of showing how to produce images server side from Java then the idea evolved from there.
The intent now for the project is to:

* create server side images in Java and provide a caching layer for these
* cookie users on each image access
* build user profiles based on the above
* also track what websites end up embedding our component
* provide reporting transparently on the above and allow users to track their own "profiles" and movements as recorded by us as well as "reset" their profiles

By providing these openly the project can make users more aware of the way other servers on the internet are tracking their movements and building a profile for them based on that.

### Creating Images

This project can create an image on the fly, based on parameters passed in the URL by the user, such that each unique combination has a unique URL which can allow browsers and proxies to cache the response. The image will consist of a text overlayed over a background of a given color. 

Parameters for the image are:

* `type` -- `png`, `jpg`
* `width` -- in pixels
* `height` -- in pixels
* `background color` -- hex RGB format, e.g. `FFFFFF` for white, `000000` for black, `FF0000` for red etc.
* `foreground color` -- font color, same format as above. 
* `font face` -- name of the font, e.g. *Sans Serif*, *Arial*, *Courier New* etc. Note that the name of the fonts have to be URL encoded. 
* `font size` -- font size in pixels
* **NOTE** that the pair `font face` + `font size` can be omitted, in which case we would use 12 px Arial as default value. However please notice that if you omit one you need to omit the other -- i.e. you can't specify just `font face` without `font size` or vice versa.
* `text` -- URL encoded, the text to be "drawn" in the image.

The text will always be center in the image both horizontally and vertically.

Example of urls:

* http://img.liviutudor.com/png/728/90/ffffff/ff0000/Arial/10/I+Like+Icarus -- will create a PNG image, 728px width and 90px height, white (0xFFFFFF) background with red (0xFF0000) text, in *Arial* font, 10 px size which reads *I Like Icarus*
* http://img.liviutudor.com/jpg/300/250/000000/ffffff/Courier+New/72/I+Like+Icarus -- will create JPEG image, 300px width and 250 px height, black (0x000000) background with white (0xFFFFFF) text, in *Courier New* font, 72px tall which reads *I Like Icarus*
 

