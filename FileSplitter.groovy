def inputFile = new File('pairs.txt')
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
    def outFile = new File("pairs_${fileKey}")
    // newWriter() overwrites the existing file
    def w = outFile.newWriter()
    println "Created new file for key ${fileKey}..."
    // Append each key,value pair
    valueMap.value.sort().each { value ->
        w << "${fileKey},${value}\n"
    }
    println "...added ${valueMap.value.size()} values to file."
    w.close()    
}