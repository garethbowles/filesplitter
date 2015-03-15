/*
  Split a file containing one key,value pair per line into one file of key,value
  pairs for each unique key

  TODO: 

  1. Pass the input file name as a parameter
  2. Use buffered input / output streams instead of reading the whole input file.

*/ 

def INPUT_FILE_NAME = 'pairs.txt'
def inputFile = new File(INPUT_FILE_NAME)
def valueLists = [:] // Maps key to a list of values for that key

inputFile.eachLine { line ->
    def fields = line.split(",")
    if (fields.length > 2) {
        println "Invalid line ${line}: should contain 2 values separated by a comma."
    } else {
        def fileKey = fields[0]
        // Create a new list if there is no map entry for this key
        valueLists[fileKey] ?: valueLists.put(fileKey, [])
        // Add value to list
        valueLists[fileKey] << fields[1]
    }   
}

valueLists.each { valueMap ->
    def fileKey = valueMap.key
    // Create a new file for each key
    def outFile = new File("${INPUT_FILE_NAME}_${fileKey}")
    // newWriter() overwrites existing file
    def w = outFile.newWriter()
    println "Created new file ${outFile.name} for key ${fileKey}..."
    // Append each key,value pair
    valueMap.value.sort().each { value ->
        w << "${fileKey},${value}\n"
    }
    println "...added ${valueMap.value.size()} values to file."
    w.close()    
}