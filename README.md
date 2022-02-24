# ChunksView

ChunksView is android view that you can benefit from as an input field in which you can enter an input separated by a provided separator.

It can be used in the following cases:
- Bank card pan number (1111-2222-3333-4444)
- Activation Code (1432 2412)
- Debit/Credit card expiry date (12/23)

As you can see from the examples above, each input is displayed as a number of chunks separator by a separator.

## Installation

To start using this library, follow the steps below :

- Add the following to your root build.gradle at the end of repositories:

```bash
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

- Add this dependency to your app level build.gradle file

```bash
dependencies {
    ...
    implementation 'com.github.MonzerYaghi:ChunksView:1.0.0'
}
```

## Usage

In your xml file, add the following

```python

 <com.monzeryaghi.chunksview.ChunksView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:inputType="number"
        app:chunkLength="4"
        app:chunkSeparator="DASH"
        app:chunksNumber="4" />

```

This view has 3 attributes:

- chunkLength : The length of each chunk (for example for the expiry date 12/23, the chunk length is 2)
- chunksNumber: The total number of chunks (for example for the bank card pan 1111-2222-3333-4444, the number of chunks is 4)
- chunkSeparator: The charactor that will separator the chunks, in the view the separators are predefined as follows (SPACE,DASH,SLASH,DOT)
